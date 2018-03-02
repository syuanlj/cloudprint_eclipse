/*
 * 
 * 
 * 
 */
package com.nfet.controller.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nfet.entity.Order;
import com.nfet.entity.Product;
import com.nfet.service.OrderService;
import com.nfet.service.ProductService;

/**
 * Controller - 统计
 * 
 * 
 * 
 */
@Controller("adminStatisticController")
@RequestMapping("/admin/statistic")
public class StatisticController extends BaseController {

	/** 最大统计数 */
	private static final int MAX_SIZE_HOUR = 24;

	private static final int MAX_SIZE_DATE = 31;

	private static final int MAX_SIZE_MONTH = 12;

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	@RequestMapping(value = "/product_hour", method = RequestMethod.GET)
	public String product_hour(Long id, Order.OrderStatus orderStatus, Date date, Model model) {
		Product product = productService.find(id);
		if (orderStatus == null) {
			orderStatus = Order.OrderStatus.printed;
		}
		if (date == null) {
			date = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();
		Calendar calendar = DateUtils.toCalendar(date);

		for (int hour = calendar.getActualMinimum(Calendar.HOUR_OF_DAY); hour <= calendar.getActualMaximum(Calendar.HOUR_OF_DAY); hour++) {
			if (printedCountMap.size() >= MAX_SIZE_HOUR) {
				break;
			}
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			Date begin = calendar.getTime();
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			Date end = calendar.getTime();
			Long printedCount = orderService.count(product, orderStatus, begin, end, false);
			printedCountMap.put(begin, printedCount);
		}
		Long printedOrderCount = orderService.count(product, Order.OrderStatus.printed, null, null, false);
		model.addAttribute("printedOrderCount", printedOrderCount);
		Long sentOrderCount = orderService.count(product, Order.OrderStatus.sent, null, null, false);
		model.addAttribute("sentOrderCount", sentOrderCount);
		Long receivedOrderCount = orderService.count(product, Order.OrderStatus.received, null, null, false);
		model.addAttribute("receivedOrderCount", receivedOrderCount);
		Long expiredOrderCount = orderService.count(product, null, null, null, true);
		model.addAttribute("expiredOrderCount", expiredOrderCount);
		model.addAttribute("orderStatuses", Order.OrderStatus.values());
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("date", date);
		model.addAttribute("printedCountMap", printedCountMap);
		model.addAttribute("product", product);
		return "/admin/statistic/product_hour";
	}

	@RequestMapping(value = "/product_date", method = RequestMethod.GET)
	public String product_date(Long id, Order.OrderStatus orderStatus, Date beginDate, Date endDate, Model model) {
		Product product = productService.find(id);
		if (orderStatus == null) {
			orderStatus = Order.OrderStatus.printed;
		}
		if (beginDate == null) {
			beginDate = DateUtils.addDays(new Date(), 1 - MAX_SIZE_DATE);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();

		Calendar beginCalendar = DateUtils.toCalendar(beginDate);
		Calendar endCalendar = DateUtils.toCalendar(endDate);
		int beginDate1 = beginCalendar.get(Calendar.DATE);
		int endDate1 = endCalendar.get(Calendar.DATE);
		int beginYear = beginCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);
		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth = endCalendar.get(Calendar.MONTH);

		for (int year = beginYear; year <= endYear; year++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			for (int month = year == beginYear ? beginMonth : calendar.getActualMinimum(Calendar.MONTH); month <= (year == endYear ? endMonth : calendar.getActualMaximum(Calendar.MONTH)); month++) {
				calendar.set(Calendar.MONTH, month);
				for (int date = month == beginMonth ? beginDate1 : calendar.getActualMinimum(Calendar.DATE); date <= (month == endMonth ? endDate1 : calendar.getActualMaximum(Calendar.DATE)); date++) {
					if (printedCountMap.size() >= MAX_SIZE_DATE) {
						break;
					}
					calendar.set(Calendar.DATE, date);
					calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
					Date begin = calendar.getTime();
					calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
					Date end = calendar.getTime();
					Long printedCount = orderService.count(product, orderStatus, begin, end, false);
					printedCountMap.put(begin, printedCount);
				}
			}
		}
		Long printedOrderCount = orderService.count(product, Order.OrderStatus.printed, null, null, false);
		model.addAttribute("printedOrderCount", printedOrderCount);
		Long sentOrderCount = orderService.count(product, Order.OrderStatus.sent, null, null, false);
		model.addAttribute("sentOrderCount", sentOrderCount);
		Long receivedOrderCount = orderService.count(product, Order.OrderStatus.received, null, null, false);
		model.addAttribute("receivedOrderCount", receivedOrderCount);
		Long expiredOrderCount = orderService.count(product, null, null, null, true);
		model.addAttribute("expiredOrderCount", expiredOrderCount);
		model.addAttribute("orderStatuses", Order.OrderStatus.values());
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("printedCountMap", printedCountMap);
		model.addAttribute("product", product);
		return "/admin/statistic/product_date";
	}

	@RequestMapping(value = "/product_month", method = RequestMethod.GET)
	public String product_month(Long id, Order.OrderStatus orderStatus, Date beginDate, Date endDate, Model model) {
		Product product = productService.find(id);
		if (orderStatus == null) {
			orderStatus = Order.OrderStatus.printed;
		}
		if (beginDate == null) {
			beginDate = DateUtils.addMonths(new Date(), 1 - MAX_SIZE_MONTH);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();

		Calendar beginCalendar = DateUtils.toCalendar(beginDate);
		Calendar endCalendar = DateUtils.toCalendar(endDate);
		int beginYear = beginCalendar.get(Calendar.YEAR);
		int endYear = endCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);
		int endMonth = endCalendar.get(Calendar.MONTH);

		for (int year = beginYear; year <= endYear; year++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			for (int month = year == beginYear ? beginMonth : calendar.getActualMinimum(Calendar.MONTH); month <= (year == endYear ? endMonth : calendar.getActualMaximum(Calendar.MONTH)); month++) {
				if (printedCountMap.size() >= MAX_SIZE_MONTH) {
					break;
				}
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date begin = calendar.getTime();
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
				Date end = calendar.getTime();
				Long printedCount = orderService.count(product, orderStatus, begin, end, false);
				printedCountMap.put(begin, printedCount);
			}
		}
		Long printedOrderCount = orderService.count(product, Order.OrderStatus.printed, null, null, false);
		model.addAttribute("printedOrderCount", printedOrderCount);
		Long sentOrderCount = orderService.count(product, Order.OrderStatus.sent, null, null, false);
		model.addAttribute("sentOrderCount", sentOrderCount);
		Long receivedOrderCount = orderService.count(product, Order.OrderStatus.received, null, null, false);
		model.addAttribute("receivedOrderCount", receivedOrderCount);
		Long expiredOrderCount = orderService.count(product, null, null, null, true);
		model.addAttribute("expiredOrderCount", expiredOrderCount);
		model.addAttribute("orderStatuses", Order.OrderStatus.values());
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("printedCountMap", printedCountMap);
		model.addAttribute("product", product);
		return "/admin/statistic/product_month";
	}

	@RequestMapping(value = "/print_hour", method = RequestMethod.GET)
	public String print_hour(Date date, Model model) {
		if (date == null) {
			date = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();
		Calendar calendar = DateUtils.toCalendar(date);

		for (int hour = calendar.getActualMinimum(Calendar.HOUR_OF_DAY); hour <= calendar.getActualMaximum(Calendar.HOUR_OF_DAY); hour++) {
			if (printedCountMap.size() >= MAX_SIZE_HOUR) {
				break;
			}
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			Date begin = calendar.getTime();
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			Date end = calendar.getTime();
			Long printedCount = orderService.count(null, Order.OrderStatus.printed, begin, end, false);
			printedCountMap.put(begin, printedCount);
		}
		Long printedOrderCount = orderService.count(null, Order.OrderStatus.printed, null, null, false);
		model.addAttribute("printedOrderCount", printedOrderCount);
		model.addAttribute("orderStatuses", Order.OrderStatus.values());
		model.addAttribute("date", date);
		model.addAttribute("printedCountMap", printedCountMap);
		return "/admin/statistic/print_hour";
	}

	@RequestMapping(value = "/print_date", method = RequestMethod.GET)
	public String print_date(Date beginDate, Date endDate, Model model) {
		if (beginDate == null) {
			beginDate = DateUtils.addDays(new Date(), 1 - MAX_SIZE_DATE);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();

		Calendar beginCalendar = DateUtils.toCalendar(beginDate);
		Calendar endCalendar = DateUtils.toCalendar(endDate);
		int beginDate1 = beginCalendar.get(Calendar.DATE);
		int endDate1 = endCalendar.get(Calendar.DATE);
		int beginYear = beginCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);
		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth = endCalendar.get(Calendar.MONTH);

		for (int year = beginYear; year <= endYear; year++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			for (int month = year == beginYear ? beginMonth : calendar.getActualMinimum(Calendar.MONTH); month <= (year == endYear ? endMonth : calendar.getActualMaximum(Calendar.MONTH)); month++) {
				calendar.set(Calendar.MONTH, month);
				for (int date = month == beginMonth ? beginDate1 : calendar.getActualMinimum(Calendar.DATE); date <= (month == endMonth ? endDate1 : calendar.getActualMaximum(Calendar.DATE)); date++) {
					if (printedCountMap.size() >= MAX_SIZE_DATE) {
						break;
					}
					calendar.set(Calendar.DATE, date);
					calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
					Date begin = calendar.getTime();
					calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
					Date end = calendar.getTime();
					Long printedCount = orderService.count(null, Order.OrderStatus.printed, begin, end, false);
					printedCountMap.put(begin, printedCount);
				}
			}
		}
		Long printedOrderCount = orderService.count(null, Order.OrderStatus.printed, null, null, false);
		model.addAttribute("printedOrderCount", printedOrderCount);
		model.addAttribute("orderStatuses", Order.OrderStatus.values());
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("printedCountMap", printedCountMap);
		return "/admin/statistic/print_date";
	}

	@RequestMapping(value = "/print_month", method = RequestMethod.GET)
	public String print_month(Date beginDate, Date endDate, Model model) {
		if (beginDate == null) {
			beginDate = DateUtils.addMonths(new Date(), 1 - MAX_SIZE_MONTH);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();

		Calendar beginCalendar = DateUtils.toCalendar(beginDate);
		Calendar endCalendar = DateUtils.toCalendar(endDate);
		int beginYear = beginCalendar.get(Calendar.YEAR);
		int endYear = endCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);
		int endMonth = endCalendar.get(Calendar.MONTH);

		for (int year = beginYear; year <= endYear; year++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			for (int month = year == beginYear ? beginMonth : calendar.getActualMinimum(Calendar.MONTH); month <= (year == endYear ? endMonth : calendar.getActualMaximum(Calendar.MONTH)); month++) {
				if (printedCountMap.size() >= MAX_SIZE_MONTH) {
					break;
				}
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date begin = calendar.getTime();
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
				Date end = calendar.getTime();
				Long printedCount = orderService.count(null, Order.OrderStatus.printed, begin, end, false);
				printedCountMap.put(begin, printedCount);
			}
		}
		Long printedOrderCount = orderService.count(null, Order.OrderStatus.printed, null, null, false);
		model.addAttribute("printedOrderCount", printedOrderCount);
		model.addAttribute("orderStatuses", Order.OrderStatus.values());
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("printedCountMap", printedCountMap);
		return "/admin/statistic/print_month";
	}

	@RequestMapping(value = "/receive_hour", method = RequestMethod.GET)
	public String receive_hour(Date date, Model model) {
		if (date == null) {
			date = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();
		Calendar calendar = DateUtils.toCalendar(date);

		for (int hour = calendar.getActualMinimum(Calendar.HOUR_OF_DAY); hour <= calendar.getActualMaximum(Calendar.HOUR_OF_DAY); hour++) {
			if (printedCountMap.size() >= MAX_SIZE_HOUR) {
				break;
			}
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			Date begin = calendar.getTime();
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			Date end = calendar.getTime();
			Long receivedCount = orderService.count(null, null, begin, end, null);
			printedCountMap.put(begin, receivedCount);
		}
		Long allOrderCount = orderService.count();
		model.addAttribute("allOrderCount", allOrderCount);
		model.addAttribute("date", date);
		model.addAttribute("printedCountMap", printedCountMap);
		return "/admin/statistic/receive_hour";
	}

	/**
	 * 系统接收订单
	 */
	@RequestMapping(value = "/receive_date", method = RequestMethod.GET)
	public String receive_date(Date beginDate, Date endDate, Model model) {
		if (beginDate == null) {
			beginDate = DateUtils.addDays(new Date(), 1 - MAX_SIZE_DATE);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();

		Calendar beginCalendar = DateUtils.toCalendar(beginDate);
		Calendar endCalendar = DateUtils.toCalendar(endDate);
		int beginDate1 = beginCalendar.get(Calendar.DATE);
		int endDate1 = endCalendar.get(Calendar.DATE);
		int beginYear = beginCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);
		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth = endCalendar.get(Calendar.MONTH);

		for (int year = beginYear; year <= endYear; year++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			for (int month = year == beginYear ? beginMonth : calendar.getActualMinimum(Calendar.MONTH); month <= (year == endYear ? endMonth : calendar.getActualMaximum(Calendar.MONTH)); month++) {
				calendar.set(Calendar.MONTH, month);
				for (int date = month == beginMonth ? beginDate1 : calendar.getActualMinimum(Calendar.DATE); date <= (month == endMonth ? endDate1 : calendar.getActualMaximum(Calendar.DATE)); date++) {
					if (printedCountMap.size() >= MAX_SIZE_DATE) {
						break;
					}
					calendar.set(Calendar.DATE, date);
					calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
					Date begin = calendar.getTime();
					calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
					calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
					calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
					Date end = calendar.getTime();
					Long receivedCount = orderService.count(null, null, begin, end, null);
					printedCountMap.put(begin, receivedCount);
				}
			}
		}
		Long allOrderCount = orderService.count();
		model.addAttribute("allOrderCount", allOrderCount);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("printedCountMap", printedCountMap);
		return "/admin/statistic/receive_date";
	}

	/**
	 * 系统接收订单
	 */
	@RequestMapping(value = "/receive_month", method = RequestMethod.GET)
	public String receive_month(Date beginDate, Date endDate, Model model) {
		if (beginDate == null) {
			beginDate = DateUtils.addMonths(new Date(), 1 - MAX_SIZE_MONTH);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		Map<Date, Long> printedCountMap = new LinkedHashMap<Date, Long>();

		Calendar beginCalendar = DateUtils.toCalendar(beginDate);
		Calendar endCalendar = DateUtils.toCalendar(endDate);
		int beginYear = beginCalendar.get(Calendar.YEAR);
		int endYear = endCalendar.get(Calendar.YEAR);
		int beginMonth = beginCalendar.get(Calendar.MONTH);
		int endMonth = endCalendar.get(Calendar.MONTH);

		for (int year = beginYear; year <= endYear; year++) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			for (int month = year == beginYear ? beginMonth : calendar.getActualMinimum(Calendar.MONTH); month <= (year == endYear ? endMonth : calendar.getActualMaximum(Calendar.MONTH)); month++) {
				if (printedCountMap.size() >= MAX_SIZE_MONTH) {
					break;
				}
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
				Date begin = calendar.getTime();
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
				calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
				calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
				Date end = calendar.getTime();
				Long receivedCount = orderService.count(null, null, begin, end, null);
				printedCountMap.put(begin, receivedCount);
			}
		}
		Long allOrderCount = orderService.count();
		model.addAttribute("allOrderCount", allOrderCount);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("printedCountMap", printedCountMap);
		return "/admin/statistic/receive_month";
	}
}