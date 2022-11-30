package org.nju.demo.utils.joanacore;

import com.ibm.wala.ipa.cha.ClassHierarchyException;
import org.nju.demo.utils.joanacore.datastructure.Func;
import org.nju.demo.utils.joanacore.datastructure.Location;
import org.nju.demo.utils.joanacore.exception.SlicerException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface Slicer {
    void config(List<File> appJars, List<URL> libJars, List<String> exclusions) throws ClassHierarchyException, IOException;
    List<Integer> computeSlice(Func func, Location line) throws SlicerException;
}
