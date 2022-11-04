package org.nju.demo.utils.algorithm;

import org.nju.demo.entity.Violation;

import java.util.List;

public interface Match {

    List<Violation> mark1(List<Violation> v1, List<Violation> v2);

}
