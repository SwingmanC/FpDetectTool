package org.nju.demo.config;

public class Constants {

    public static final String ROOT_PATH = "/Users/sunchen/repo/";

    public static final String DATA_FILE_NAME = "violation_code";

    public static final String SNAPSHOT_FILE_NAME = "snapshot";

    public static class Type {
//        public static String[] findBugs = {"BAD_PRACTICE","DODGY_CODE","MALICIOUS_CODE","CORRECTNESS","Multithreaded correctness","PERFORMANCE","I18N","SECURITY","EXPERRIMENTAL"};

        public static String[] findBugs = {"BAD_PRACTICE","STYLE","MALICIOUS_CODE","CORRECTNESS","MT_CORRECTNESS","PERFORMANCE","I18N","SECURITY","EXPERRIMENTAL"};

        public static String[] PMD = {"Best Practices","Code Style","Design","Documentation","Error Prone","Multithreading","Performance","Security"};

        public static String[] combine = {"Check","Concurrency","Error Handling","Interface","Logic","Migration","Resource","Best Practices","Code Structure","Design","Documentation","Metrics","Naming","Redundancies","Simplifications","Style Conventions","Security"};
    }

    public static class ViolationState{
        public static final int UNCLASSIFIED = -1;
        public static final int UNKNOWN = 2;
        public static final int TRUE = 1;
        public static final int FALSE = 0;
    }

    public static class IsFilter{
        public static final int IGNORE = -1;
        public static final int ENABLE = 1;
        public static final int DISABLE = 0;
    }

    public static class IsEnable{
        public static final int YES = 1;
        public static final int NO = 0;
    }

    public static class VersionState{
        public static final String INIT = "Null";
        public static final String FIRST = "First";
    }

    public static class TemplateState{
        public static final int ENABLE = 1;
        public static final int DISABLE = 0;
    }

    public static class UserInfo{
        public static final String EMAIL = "123@nju.edu.cn";
        public static final String TELEPHONE = "10000";
    }

    public static final String testProjectName = "maven-dependency-plugin";
    public static final int FIXED_LENGTH = 59;

}
