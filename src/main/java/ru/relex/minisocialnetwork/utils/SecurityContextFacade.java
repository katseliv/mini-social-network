package ru.relex.minisocialnetwork.utils;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextFacade {

    SecurityContext getContext();

}
