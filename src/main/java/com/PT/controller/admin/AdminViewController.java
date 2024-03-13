package com.PT.controller.admin;

import com.PT.service.packaze.PackageService;
import com.PT.service.pass.BulkPassService;
import com.PT.service.user.UserGroupMappingService;
import com.PT.util.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/admin")
public class AdminViewController {
    @Autowired
    private BulkPassService bulkPassService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private UserGroupMappingService userGroupMappingService;
//    @Autowired
//    private StatisticsService statisticsService;

//    @GetMapping
//    public ModelAndView home(ModelAndView modelAndView, @RequestParam("to") String toString) {
//        LocalDateTime to = LocalDateTimeUtils.parseDate(toString);
//
//        modelAndView.addObject("chartData", statisticsService.makeChartData(to));
//        modelAndView.setViewName("admin/index");
//        return modelAndView;
//    }

    public AdminViewController(BulkPassService bulkPassService, PackageService packageService, UserGroupMappingService userGroupMappingService) {
        this.bulkPassService = bulkPassService;
        this.packageService = packageService;
        this.userGroupMappingService = userGroupMappingService;
    }

    @GetMapping("/bulk-pass")
    public ModelAndView registerBulkPass(ModelAndView modelAndView) {
        //bulk pass 목록 조회
        modelAndView.addObject("bulkPasses", bulkPassService.getAllBulkPasses());
        //bulk pass를 등록할 때 필요한 package값을 위해 모든 package를 조회
        modelAndView.addObject("packages", packageService.getAllPackages());
        //bulk pass를 등록할 때 필요한 userGroupId값을 위해 모든 userGroupId 조회
        modelAndView.addObject("userGroupIds", userGroupMappingService.getAllUserGroupIds());
        //bulk pass request 제공
        modelAndView.addObject("request", new BulkPassRequest());
        modelAndView.setViewName("admin/bulk-pass");

        return modelAndView;
    }

    @PostMapping("/bulk-pass")
    public String addBulkPass(@ModelAttribute("request") BulkPassRequest request, Model model) {
        //bulk pass 생성
        bulkPassService.addBulkPass(request);
        return "redirect:/admin/bulk-pass";
    }
}
