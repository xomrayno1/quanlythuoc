<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
	<div class="menu_section">
		<h3>General</h3>
			<ul class="nav side-menu" id="home">
				<li><a href='<c:url value="/index"></c:url>'><i class="fa fa-home"></i>Trang chủ  </a>					
				</li>
				
			</ul>
			 
				<ul class="nav side-menu" id="mainAct">
					<li><a><i class="fa fa-tasks"></i>Tác vụ chính<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
							 					
						</ul>
					</li>
				</ul>	 
				<ul class="nav side-menu" id="manage">
					<li><a><i class="fa fa-users"></i>Quản lý<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
							<li id="drugList"><a href='<c:url value="/manage/drug/list"></c:url>'>Thuốc</a></li>
							<li id="supplierlist"><a href='<c:url value="/manage/supplier/list"></c:url>'>Nhà cung cấp </a></li>
							<li id="userlist"><a href='<c:url value="/manage/user/list"></c:url>'>Tài khoản</a></li>		
						</ul>
					</li>
				</ul>
				
				<ul class="nav side-menu" id="statistics">
					<li><a><i class="fa fa-bar-chart"></i>Thống kê<span class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu">
						 	
						</ul>
					</li>
				</ul>
 
			 
	</div>
</div>