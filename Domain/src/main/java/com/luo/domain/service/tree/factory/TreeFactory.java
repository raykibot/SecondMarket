package com.luo.domain.service.tree.factory;

import com.luo.domain.service.tree.ILockTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TreeFactory {

    private final Map<String,ILockTree> lockTreeGroups;

    public TreeFactory(Map<String, ILockTree> lockTreeGroups) {
        this.lockTreeGroups = lockTreeGroups;
    }

    public Map<String,ILockTree> getNode(){
        return lockTreeGroups;
    }
}
