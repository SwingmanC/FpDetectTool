package org.nju.demo.service.impl;

import com.ibm.wala.ipa.cha.ClassHierarchyException;
import org.nju.demo.config.Constants;
import org.nju.demo.dao.ViolationSliceMapper;
import org.nju.demo.entity.*;
import org.nju.demo.service.SliceService;
import org.nju.demo.utils.joanacore.JoanaSlicer;
import org.nju.demo.utils.joanacore.datastructure.Func;
import org.nju.demo.utils.joanacore.datastructure.Location;
import org.nju.demo.utils.joanacore.exception.SlicerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class SliceServiceImpl implements SliceService {

    @Autowired
    private ViolationSliceMapper violationSliceMapper;

    @Override
    public ViolationSliceWithBLOBs getViolationSliceByViolationId(int violationId) {
        ViolationSliceExample example = new ViolationSliceExample();
        ViolationSliceExample.Criteria criteria = example.createCriteria();

        criteria.andViolationIdEqualTo(violationId);

        List<ViolationSliceWithBLOBs> violationSliceList = violationSliceMapper.selectByExampleWithBLOBs(example);
        if (violationSliceList.size() > 0) return violationSliceList.get(0);
        else return null;
    }

    @Override
    public ViolationSliceWithBLOBs getViolationSlice(int sliceId) {
        return violationSliceMapper.selectByPrimaryKey(sliceId);
    }

    @Override
    public int addViolationSlice(ViolationSliceWithBLOBs violationSlice) {
        return violationSliceMapper.insert(violationSlice);
    }

    @Override
    public int addViolationSlice(AVersion version,Violation violation) {
        if (violation.getStartLine() == -1 || getViolationSliceByViolationId(violation.getId()) != null) {
            System.out.println("警告"+violation.getId()+"切片已存在");
            return -1;
        }
        ViolationSliceWithBLOBs violationSlice = new ViolationSliceWithBLOBs();
        List<File> apps = new ArrayList<>();
        apps.add(new File(Constants.ROOT_PATH+version.getJarFilePath()));
        JoanaSlicer slicer = new JoanaSlicer();
        try {
            slicer.config(apps,null , null);
            List<Integer> lineList = slicer.computeSlice(new Func(violation.getClassName(),violation.getMethodName(),violation.getSignature()),
                    new Location(violation.getSourcePath(),violation.getStartLine(),violation.getEndLine()));
            int max = 0;
            List<Integer> res = new ArrayList<>();
            for (Integer i : lineList){
                if (i == 0 || i == max) continue;
                if (i < max) break;
                max = i;
                res.add(i);
            }
            StringBuilder sb = new StringBuilder();
            String filePath = Constants.ROOT_PATH + version.getJavaFilePath() + "/" + violation.getSourcePath();
            int cnt = 0,index = 0;
            InputStream inputStream = Files.newInputStream(Paths.get(filePath));
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while (index < res.size() && (line = br.readLine()) != null) {
                    cnt++;
                    if(cnt == res.get(index)){
                        sb.append(line).append("\n");
                        index++;
                    }
                }
            }
            violationSlice.setViolationId(violation.getId());
            violationSlice.setSnippet(sb.toString());
            System.out.println("警告"+violation.getId()+"切片提取成功");
            return violationSliceMapper.insert(violationSlice);
        } catch (ClassHierarchyException e) {
            System.out.println("警告"+violation.getId()+"切片提取失败");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("警告"+violation.getId()+"切片提取失败");
            throw new RuntimeException(e);
        } catch (SlicerException e) {
            System.out.println("警告"+violation.getId()+"切片提取失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateViolationSlice(ViolationSliceWithBLOBs violationSlice) {
        return violationSliceMapper.updateByPrimaryKeyWithBLOBs(violationSlice);
    }

    @Override
    public int deleteViolationSliceByViolationId(int violationId) {
        ViolationSliceExample example = new ViolationSliceExample();
        ViolationSliceExample.Criteria criteria = example.createCriteria();

        criteria.andViolationIdEqualTo(violationId);

        return violationSliceMapper.deleteByExample(example);
    }

    @Override
    public int deleteViolationSliceById(int sliceId) {
        return violationSliceMapper.deleteByPrimaryKey(sliceId);
    }
}
