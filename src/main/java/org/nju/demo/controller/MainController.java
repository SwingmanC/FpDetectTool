package org.nju.demo.controller;

import freemarker.template.TemplateException;
import org.nju.demo.config.Constants;
import org.nju.demo.entity.*;
import org.nju.demo.pojo.dto.PatternStatisticsDTO;
import org.nju.demo.pojo.vo.*;
import org.nju.demo.service.*;
import org.nju.demo.utils.*;
import org.nju.demo.utils.algorithm.Match;
import org.nju.demo.utils.algorithm.impl.ExactMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private PatternService patternService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private HttpSession session;

    @RequestMapping("/view/projects")
    public String viewProjects(){
        return "project_list";
    }

    @RequestMapping("/view/versions/{projectId}")
    public String viewVersions(@PathVariable("projectId") String projectId,
                               Model model){
        Project project = projectService.getProject(projectId);
        List<AVersion> versionList = versionService.getVersionsByProjectId(projectId);
        session.setAttribute("project",project);
        if (versionList.size() != 0)
            model.addAttribute("versionList",versionList);
        return "version_list";
    }

    @RequestMapping("/back_versions")
    public String backVersions(Model model){
        Project project = (Project) session.getAttribute("project");
        List<AVersion> versionList = versionService.getVersionsByProjectId(project.getProjectId());
        if (versionList.size() != 0)
            model.addAttribute("versionList",versionList);
        return "version_list";
    }

    @RequestMapping("/view/violations/{versionId}")
    public String viewViolations(@PathVariable("versionId") String versionId,Model model){
        AVersion version = versionService.getVersion(versionId);
        List<PatternStatisticsDTO> patternStatisticsDTOList = statisticsService.getPatternStatisticsByVersionId(versionId);
        model.addAttribute("patternItemList",patternStatisticsDTOList);
        session.setAttribute("version",version);
        return "violation_list";
    }

    @ResponseBody
    @RequestMapping("/projects")
    public List<Project> getProjects(){
        AUser user = (AUser) session.getAttribute("user");
        return projectService.getProjects(user.getId());
    }

    @ResponseBody
    @RequestMapping("/versions")
    public List<AVersion> getVersions(){
        Project project = (Project) session.getAttribute("project");
        return versionService.getVersionsByProjectId(project.getProjectId());
    }

    @ResponseBody
    @RequestMapping("/violations")
    public List<ViolationVO> getViolations(@RequestParam(value = "priority",required = false) String strPriority,
                                           @RequestParam(value = "patternId",required = false) String strPatternId,
                                           @RequestParam(value = "state",required = false) String strState){
        AVersion version = (AVersion) session.getAttribute("version");
        List<Violation> violationList = null;
        int priority=0,state=-2;
        String type = "";
        if (strPriority !=null && !strPriority.equals("")){
            priority = Integer.parseInt(strPriority);
        }
        if (strState != null && !strState.equals("")){
            state = Integer.parseInt(strState);
        }
        if (strPatternId != null && !strPatternId.equals("")){
            Pattern p = patternService.getPattern(Integer.parseInt(strPatternId));
            type = p.getPatternName();
        }
        violationList = violationService.getViolationList(version.getVersionId(),priority,type,state);

        List<ViolationVO> violationVOList = new ArrayList<>();
        for(Violation violation : violationList){
            ViolationVO violationVO = new ViolationVO();
            Pattern pattern = patternService.getPatternByPatternName(violation.getType());
            violationVO.setId(violation.getId());
            violationVO.setType(violation.getType());
            violationVO.setSourcePath(violation.getSourcePath());
            violationVO.setCategory(violation.getCategory());
            violationVO.setClassName(violation.getClassName());
            violationVO.setMethodName(violation.getMethodName());
            violationVO.setPriority(violation.getPriority());
            violationVO.setStartLine(violation.getStartLine());
            violationVO.setEndLine(violation.getEndLine());

            int trueNum = pattern.gettNum();
            int falseNum = pattern.getfNum();

            if (trueNum == 0 && falseNum == 0){
                violationVO.setLikelihood(0);
                violationVO.setVariance(1);
            }
            else{
                long n = patternService.countByCategoryId(pattern.getCategoryId());
                double likelihood = trueNum*1.0/(trueNum+falseNum);
                double variance = likelihood*(1-likelihood)/n;

                violationVO.setLikelihood(likelihood);
                violationVO.setVariance(variance);
                violationVO.setState(violation.getState());
            }

            violationVOList.add(violationVO);
        }
        return violationVOList;
    }

    @ResponseBody
    @RequestMapping("/start/violation/{id}")
    public int startViolation(@PathVariable("id") int id){
        Violation violation = violationService.getViolation(id);
        Pattern pattern = patternService.getPatternByPatternName(violation.getType());

        if (violation.getState() == 0) pattern.setfNum(pattern.getfNum()-1);
        pattern.settNum(pattern.gettNum()+1);
        patternService.updatePattern(pattern);

        violation.setState(1);
        return violationService.updateViolation(violation);
    }

    @ResponseBody
    @RequestMapping("/end/violation/{id}")
    public int endViolation(@PathVariable("id") int id){
        Violation violation = violationService.getViolation(id);
        Pattern pattern = patternService.getPatternByPatternName(violation.getType());

        if (violation.getState() == 1) pattern.settNum(pattern.gettNum()-1);
        pattern.setfNum(pattern.getfNum()+1);
        patternService.updatePattern(pattern);

        violation.setState(0);
        return violationService.updateViolation(violation);
    }

    @ResponseBody
    @RequestMapping("/violation/{violationId}")
    public Violation getViolation(@PathVariable("violationId") int violationId){
        return violationService.getViolation(violationId);
    }

    @ResponseBody
    @RequestMapping(value = "/addProject",method = RequestMethod.POST)
    public int addProjects(@RequestParam("projectName") String projectName,
                           @RequestParam("description") String description){
        Project project = new Project();
        AUser user = (AUser) session.getAttribute("user");

        List<Project> projectList = projectService.getProjectsByProjectName(user.getId(),projectName);
        if(projectList.size()>0) return -1;

        project.setProjectId(StringUtil.generateStringId());
        project.setUserId(user.getId());
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setCreateTime(new Date());

        return projectService.addProject(project);
    }

    @RequestMapping("/addVersion")
    public String addVersion(@RequestParam("versionName") String versionName,
                             @RequestParam("javaFiles") MultipartFile[] javaFiles,
                             @RequestParam("classFiles") MultipartFile[] classFiles) throws IOException {
        AVersion aVersion = new AVersion();
        AUser user = (AUser) session.getAttribute("user");
        Project project = (Project) session.getAttribute("project");

        List<AVersion> versionList = versionService.getVersionsByVersionName(project.getProjectId(),versionName);
        if (versionList.size()!=0) return "redirect:/back_versions";

        File file = null;
        if(classFiles != null && classFiles.length > 0){
            for (int i=0;i<classFiles.length;++i){
                String fileName = classFiles[i].getOriginalFilename();
                String[] paths = fileName.split("/");
                String filePath = Constants.ROOT_PATH + "/data/"+user.getUsername()+"/"+project.getProjectName()+"/"+versionName+"/classes/";
                for (int j=0;j<paths.length-1;++j){
                    filePath += paths[j];
                    filePath += "/";
                }
                file = new File(filePath);
                if (!file.exists()) file.mkdirs();
                filePath += paths[paths.length-1];
                file = new File(filePath);
                classFiles[i].transferTo(file);
            }
        }

        if(javaFiles != null && javaFiles.length > 0){
            for (int i=0;i<javaFiles.length;++i){
                String fileName = javaFiles[i].getOriginalFilename();
                String[] paths = fileName.split("/");
                String filePath = Constants.ROOT_PATH + "/data/"+user.getUsername()+"/"+project.getProjectName()+"/"+versionName+"/sources/";
                for (int j=0;j<paths.length-1;++j){
                    filePath += paths[j];
                    filePath += "/";
                }
                file = new File(filePath);
                if (!file.exists()) file.mkdirs();
                filePath += paths[paths.length-1];
                file = new File(filePath);
                javaFiles[i].transferTo(file);
            }
        }

        aVersion.setVersionId(StringUtil.generateStringId());
        aVersion.setVersionName(versionName);
        aVersion.setLastId(Constants.VersionState.INIT);
        aVersion.setClassFilePath("data/"+user.getUsername()+"/"+project.getProjectName()+"/"+versionName+"/classes");
        aVersion.setJavaFilePath("data/"+user.getUsername()+"/"+project.getProjectName()+"/"+versionName+"/sources");
        aVersion.setProjectId(project.getProjectId());
        aVersion.setCreateTime(new Date());
        versionService.addVersion(aVersion);
        return "redirect:/back_versions";
    }

    @ResponseBody
    @RequestMapping("/version")
    public int getVersion(@RequestParam("versionName") String versionName){
        Project project = (Project) session.getAttribute("project");
        List<AVersion> versionList = versionService.getVersionsByVersionName(project.getProjectId(),versionName);
        if (versionList.size()!=0) return 1;
        else return 0;
    }

    @ResponseBody
    @RequestMapping("/editVersion")
    public int editVersion(@RequestParam("versionId") String versionId,
                           @RequestParam(value = "lastId") String lastId){
        AVersion version = versionService.getVersion(versionId);
        version.setLastId(lastId);
        return versionService.updateVersion(version);
    }

    @ResponseBody
    @RequestMapping("/scan")
    public int scan(@RequestParam("versionId") String versionId){
        if (violationService.getViolationsByVersionId(versionId).size() != 0) return 2;

        Project project = (Project) session.getAttribute("project");
        AVersion aVersion = versionService.getVersion(versionId);
        if (aVersion.getLastId().equals(Constants.VersionState.INIT)) return 3;
        if (!aVersion.getLastId().equals(Constants.VersionState.FIRST) && violationService.getViolationsByVersionId(aVersion.getLastId()).size() == 0) return 4;

        try{
            String filePath = CmdUtil.generateFindBugsXML(project.getProjectName(),aVersion.getVersionName(),aVersion.getClassFilePath());
            InputStream file = new FileInputStream(filePath);
            List<Violation> v2 = XMLUtil.getViolations(file,versionId);

            Map<String,Integer> hm = statisticsService.countViolationByPattern(v2);

            for (Map.Entry<String,Integer> entry : hm.entrySet()){
                String patternName = entry.getKey();
                int cnt = entry.getValue();
                Pattern pattern = patternService.getPatternByPatternName(patternName);
                VersionPatternRel versionPatternRel = new VersionPatternRel();
                versionPatternRel.setPatternId(pattern.getId());
                versionPatternRel.setVersionId(versionId);
                int vpid = violationService.addRelation(versionPatternRel);
                PatternStatistics patternStatistics = new PatternStatistics();
                patternStatistics.setvPId(vpid);
                patternStatistics.setViolationNum(cnt);
                statisticsService.addPatternStatistics(patternStatistics);
            }

            String lastVersionId = aVersion.getLastId();
            AVersion lastVersion = versionService.getVersion(lastVersionId);

            List<Violation> v1 = null;
            if(lastVersion != null) v1 = violationService.getViolationsByVersionId(lastVersion.getVersionId());
            Match match = new ExactMatch();
            List<Violation> res = match.mark1(v1,v2);

            for (Violation violation : v2){
                int violationId = violationService.addViolation(violation);
                if (aVersion.getJavaFilePath() != null && !aVersion.getJavaFilePath().equals("")){
                    String sourcePath = violation.getSourcePath();
                    int startLine = violation.getStartLine();
                    int endLine = violation.getEndLine();
                    InputStream inputStream = Files.newInputStream(Paths.get(Constants.ROOT_PATH + aVersion.getJavaFilePath() + "/" + sourcePath));
                    String snippet = CodeUtil.readCodeFromData(inputStream,startLine,endLine);
                    if (!snippet.equals("")){
                        ViolationCode violationCode = new ViolationCode();
                        violationCode.setViolationId(violationId);
                        violationCode.setSnippet(snippet);
                        violationService.addViolationCode(violationCode);
                    }
                }
            }

            if (res != null){
                for(Violation violation : res) {
                    violationService.updateViolation(violation);
                }
                Map<String,Integer> tNums = SortUtil.countNum(res,Constants.ViolationState.TRUE);
                Map<String,Integer> fNums = SortUtil.countNum(res,Constants.ViolationState.FALSE);
                for(Map.Entry<String,Integer> entry:tNums.entrySet()){
                    String patternName = entry.getKey();
                    int trueNum = entry.getValue();
                    int falseNum = 0;
                    if (fNums.containsKey(patternName)) falseNum = fNums.get(patternName);
                    Pattern pattern = patternService.getPatternByPatternName(patternName);
                    pattern.settNum(pattern.gettNum()+trueNum);
                    pattern.setfNum(pattern.getfNum()+falseNum);
                    patternService.updatePattern(pattern);
                }
                for (Map.Entry<String,Integer> entry:fNums.entrySet()){
                    String patternName = entry.getKey();
                    if (tNums.containsKey(patternName)) continue;
                    else{
                        int falseNum = entry.getValue();
                        Pattern pattern = patternService.getPatternByPatternName(patternName);
                        pattern.setfNum(pattern.getfNum()+falseNum);
                        patternService.updatePattern(pattern);
                    }
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @ResponseBody
    @RequestMapping("/generateReport")
    public String generateReport(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, ParserConfigurationException, SAXException {

        AUser user = (AUser) session.getAttribute("user");
        Project project = (Project) session.getAttribute("project");
        AVersion version = (AVersion) session.getAttribute("version");

        List<Violation> violationList = violationService.getViolationsByVersionId(version.getVersionId());
        ATemplate template = templateService.getUsedTemplate(user.getId());

        if (template == null) return "null";

        List<ViolationDocVO> violationDocVOList = new ArrayList<>();
        for (Violation violation : violationList){
            ViolationDocVO violationDocVO = new ViolationDocVO();
            violationDocVO.setId(violation.getId());
            violationDocVO.setType(violation.getType());
            violationDocVO.setClassName(violation.getClassName());
            violationDocVO.setSourcePath(violation.getSourcePath());

            String methodName = violation.getMethodName();
            if (methodName.equals("<init>")) violationDocVO.setMethodName("init");
            else violationDocVO.setMethodName(methodName);

            violationDocVO.setPriority(violation.getPriority());
            violationDocVO.setStartLine(violation.getStartLine());
            violationDocVO.setEndLine(violation.getEndLine());
            violationDocVO.setDescription("这是一个漏洞");
            violationDocVO.setAdvice("建议立刻进行修复");
            violationDocVOList.add(violationDocVO);
        }
        DocUtil.generateDoc(violationDocVOList,project.getProjectName(),version.getVersionName(),user.getUsername(),template.getFilePath());

        String filePath = Constants.ROOT_PATH+"doc/"+user.getUsername()+"/"+project.getProjectName()+"/"+version.getVersionName()+".doc";
        File file = new File(filePath);
        StringBuilder sb = new StringBuilder();
        try{
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename="+version.getVersionName()+".doc");
            FileReader fr = new FileReader(file);
            char[] buffer = new char[23];
            int length;
            while ((length = fr.read(buffer)) != -1) {
                sb.append(buffer,0,length);
            }
            fr.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    @ResponseBody
    @RequestMapping("/deleteProject/{projectId}")
    public int deleteProject(@PathVariable("projectId") String projectId){
        List<AVersion> versionList = versionService.getVersionsByProjectId(projectId);
        for (AVersion version : versionList){
            violationService.deleteViolationByVersionId(version.getVersionId());
        }
        versionService.deleteVersionByProjectId(projectId);
        return projectService.deleteProject(projectId);
    }

    @ResponseBody
    @RequestMapping("/feature")
    public int extractFeature() throws IOException {
        AUser user = (AUser) session.getAttribute("user");
        DataUtil.generateTrainArff(user.getUsername(),featureService.getFeatureList(violationService.getClassifiedViolations()));
        return 1;
    }

}
