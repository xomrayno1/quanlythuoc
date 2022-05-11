package com.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dto.Paging;
import com.app.entity.Drug;
import com.app.service.DrugService;
import com.app.utils.Constant;
import com.app.validator.DrugValidator;

@Controller
@RequestMapping("/manage/drug")
public class DrugController {
	@Autowired
	DrugService drugService;
	@Autowired
	DrugValidator drugValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
		if(dataBinder.getTarget().getClass() == Drug.class) {
			dataBinder.setValidator(drugValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/manage/drug/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listProduct(Model model,@ModelAttribute("searchForm") Drug drug,@PathVariable("page") int page
			,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Drug> list = drugService.getAll(drug, paging);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", paging);
		if(session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		if(session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		return "drug-list";
	}
	@GetMapping(value = {"/add"})
	public String addProduct(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new Drug());
		model.addAttribute("viewOnly", false);
		return "drug-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewProduct(Model model,@PathVariable("id") Long id) {
		Drug drug = drugService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", drug);
		model.addAttribute("viewOnly", true);
		return "drug-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editProduct(Model model,@PathVariable("id") Long id) {
		Drug drug = drugService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", drug);
		model.addAttribute("viewOnly", false);
		return "drug-action";
	}
	@PostMapping(value = {"/save"})
	public String saveProduct(Model model,@ModelAttribute("submitForm") @Validated Drug drug 
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			if(drug.getId() != null) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			model.addAttribute("submitForm", drug);
			return "drug-action";
		}
		if(drug.getId() != null) {
			try {
				drugService.update(drug);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				drugService.add(drug);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/manage/drug/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteProduct(Model model,@PathVariable("id") Long id,HttpSession session) {
		Drug drug = drugService.findById(id);
		try {
			drugService.delete(drug);
			session.setAttribute(Constant.MSG_SUCCESS, "Xóa thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xóa thất bại");
		}

		return "redirect:/manage/drug/list/1";
	}
}
