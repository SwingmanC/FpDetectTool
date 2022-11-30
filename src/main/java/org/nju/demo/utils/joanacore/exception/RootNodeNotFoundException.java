package org.nju.demo.utils.joanacore.exception;

public class RootNodeNotFoundException extends NotFoundException{
    public RootNodeNotFoundException(Object element, Object container) {
        super(element, container);
    }
}
