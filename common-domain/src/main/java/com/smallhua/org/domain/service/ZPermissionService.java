package com.smallhua.org.domain.service;

import java.util.Set;

public interface ZPermissionService {

    Set<String> getPermissionValues(Long userId);
}