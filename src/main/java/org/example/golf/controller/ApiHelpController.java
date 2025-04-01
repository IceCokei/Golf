package org.example.golf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/help")
public class ApiHelpController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getApiHelp() {
        Map<String, Object> apiHelp = new HashMap<>();
        
        // 认证API
        Map<String, String> authApis = new HashMap<>();
        authApis.put("POST /api/auth/login", "登录接口，需要提供username和password");
        authApis.put("POST /api/auth/logout", "登出接口，清除客户端token");
        authApis.put("GET /api/auth/me", "获取当前登录用户信息");
        
        // 用户管理API
        Map<String, String> userApis = new HashMap<>();
        userApis.put("GET /api/users", "获取用户列表，支持分页和筛选，需要ADMIN角色");
        userApis.put("POST /api/users", "创建新用户，需要ADMIN角色");
        userApis.put("PUT /api/users/{id}", "更新指定用户信息，需要ADMIN角色");
        userApis.put("DELETE /api/users/{id}", "删除指定用户，需要ADMIN角色");
        
        // 角色与权限管理API
        Map<String, String> roleApis = new HashMap<>();
        roleApis.put("GET /api/roles", "获取角色列表，需要ADMIN角色");
        roleApis.put("POST /api/roles", "创建新角色，需要ADMIN角色");
        roleApis.put("PUT /api/roles/{id}", "更新角色权限，需要ADMIN角色");
        roleApis.put("DELETE /api/roles/{id}", "删除角色，需要ADMIN角色");
        roleApis.put("GET /api/permissions", "获取权限列表，需要ADMIN角色");
        
        // 场馆信息管理API
        Map<String, String> venueApis = new HashMap<>();
        venueApis.put("GET /api/venues", "获取场馆列表，支持分页和筛选");
        venueApis.put("POST /api/venues", "创建新场馆，需要ADMIN角色");
        venueApis.put("PUT /api/venues/{id}", "更新场馆信息，需要ADMIN角色");
        venueApis.put("DELETE /api/venues/{id}", "删除场馆，需要ADMIN角色");
        venueApis.put("POST /api/venues/upload-image", "上传场馆图片，需要ADMIN角色");
        
        // 场地管理API
        Map<String, String> fieldApis = new HashMap<>();
        fieldApis.put("GET /api/fields", "获取场地列表，支持分页和筛选");
        fieldApis.put("POST /api/fields", "创建新场地，需要ADMIN角色");
        fieldApis.put("PUT /api/fields/{id}", "更新场地信息，需要ADMIN角色");
        fieldApis.put("DELETE /api/fields/{id}", "删除场地，需要ADMIN角色");
        fieldApis.put("PUT /api/fields/{id}/status", "更新场地状态，需要ADMIN角色");
        
        // 预约管理API
        Map<String, String> bookingApis = new HashMap<>();
        bookingApis.put("GET /api/bookings", "获取预约订单列表，支持分页和筛选");
        bookingApis.put("POST /api/bookings", "创建新预约订单");
        bookingApis.put("PUT /api/bookings/{id}", "更新预约订单状态");
        bookingApis.put("DELETE /api/bookings/{id}", "删除预约订单，需要ADMIN角色");
        bookingApis.put("GET /api/bookings/statistics", "获取预约统计数据，需要ADMIN角色");
        
        // 课程管理API
        Map<String, String> courseApis = new HashMap<>();
        courseApis.put("GET /api/courses", "获取课程列表，支持分页和筛选");
        courseApis.put("POST /api/courses", "创建新课程，需要ADMIN角色");
        courseApis.put("PUT /api/courses/{id}", "更新课程信息，需要ADMIN角色");
        courseApis.put("DELETE /api/courses/{id}", "删除课程，需要ADMIN角色");
        courseApis.put("GET /api/courses/{id}/registrations", "查看课程报名情况，需要ADMIN角色");
        
        // 评价管理API
        Map<String, String> reviewApis = new HashMap<>();
        reviewApis.put("GET /api/reviews", "获取评价列表，支持分页和筛选");
        reviewApis.put("POST /api/reviews", "创建新评价");
        reviewApis.put("PUT /api/reviews/{id}", "审核评价内容，需要ADMIN角色");
        reviewApis.put("DELETE /api/reviews/{id}", "删除评价，需要ADMIN角色");
        reviewApis.put("GET /api/reviews/statistics", "获取评价统计数据，需要ADMIN角色");
        
        // 数据统计与分析API
        Map<String, String> statisticsApis = new HashMap<>();
        statisticsApis.put("GET /api/statistics/venues", "获取场馆使用率统计数据，需要ADMIN角色");
        statisticsApis.put("GET /api/statistics/bookings", "获取预约趋势统计数据，需要ADMIN角色");
        statisticsApis.put("GET /api/statistics/courses", "获取课程报名统计数据，需要ADMIN角色");
        
        // 日志管理API
        Map<String, String> logApis = new HashMap<>();
        logApis.put("GET /api/logs/login", "获取登录日志，需要ADMIN角色");
        logApis.put("GET /api/logs/operations", "获取操作日志，需要ADMIN角色");
        
        // 组装所有API
        apiHelp.put("认证API", authApis);
        apiHelp.put("用户管理API", userApis);
        apiHelp.put("角色与权限管理API", roleApis);
        apiHelp.put("场馆信息管理API", venueApis);
        apiHelp.put("场地管理API", fieldApis);
        apiHelp.put("预约管理API", bookingApis);
        apiHelp.put("课程管理API", courseApis);
        apiHelp.put("评价管理API", reviewApis);
        apiHelp.put("数据统计与分析API", statisticsApis);
        apiHelp.put("日志管理API", logApis);
        
        return ResponseEntity.ok(apiHelp);
    }
} 