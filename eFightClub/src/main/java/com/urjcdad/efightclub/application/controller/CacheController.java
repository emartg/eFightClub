package com.urjcdad.efightclub.application.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.urjcdad.efightclub.application.model.AuxiliarEventUsers;
import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Matches;
import com.urjcdad.efightclub.application.model.Notification;
import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.EventRepository;
import com.urjcdad.efightclub.application.repository.MatchesRepository;
import com.urjcdad.efightclub.application.repository.NotificationRepository;
import com.urjcdad.efightclub.application.repository.UsersRepository;
import com.urjcdad.efightclub.application.service.EventService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
	
	@Autowired
	private CacheManager cacheManager;
	
	 @GetMapping(value="/cache")
	 public Map<Object, Object> getCacheContent() {
	     ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
	     ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("notificaciones");
	     return cache.getNativeCache();
	 }
	 
}
