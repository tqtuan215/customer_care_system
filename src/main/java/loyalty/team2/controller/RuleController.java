package loyalty.team2.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import loyalty.team2.model.Customer;
import loyalty.team2.model.CustomerActionWhy;
import loyalty.team2.model.FinalAction;
import loyalty.team2.service.RuleService;
import loyalty.team2.service.RuleService2;

@RestController
public class RuleController {

	@Autowired
	private RuleService ruleSv;
	@Autowired
	private RuleService2 ruleSv2;
	
	@PostMapping("/api/upload-excel/2")
	public ResponseEntity<List<FinalAction>> uploadExcel2(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("start: ");
		List<Customer> customers = new ArrayList<>();
		if (!file.isEmpty()) {
			try {
				InputStream inputStream = file.getInputStream();
				// Lấy file Excel từ request
				Workbook workbook = WorkbookFactory.create(inputStream);

				// Lấy sheet đầu tiên
				Sheet sheet = workbook.getSheetAt(0);
				System.out.println("check: ");
				// Lấy dữ liệu từ sheet
				for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
					// Kiểm tra xem ô đầu tiên của cột đó có giá trị là "id" hay không
					Cell cell = sheet.getRow(0).getCell(i);
					System.out.println("i " + i + " - cell: " + cell.getStringCellValue());
					if (cell.getStringCellValue().equals("id")) {
						// Tạo một mảng để chứa dữ liệu của cột đó
						Integer[] data = new Integer[sheet.getPhysicalNumberOfRows() - 1];

						// Lặp qua các hàng trong sheet, bắt đầu từ hàng thứ hai
						for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
							// Lấy dữ liệu từ ô thứ hai của cột đó
							Cell cell2 = sheet.getRow(j).getCell(i);

							// Thêm dữ liệu đó vào mảng
							data[j - 1] = (int) cell2.getNumericCellValue();
						}

						// In dữ liệu trong mảng
						for (Integer value : data) {
							Customer cus = new Customer();
							cus.setCustomerId((value));
							customers.add(cus);
						}

						// Thoát khỏi vòng lặp
						break;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error: " + e);
			}
		}
		for (Customer x : customers)
			System.out.println(x.getCustomerId());
		return new ResponseEntity<List<FinalAction>>(ruleSv2.ruleExcelEvent(customers), HttpStatus.OK);
	}
	
	
	@PostMapping("/api/upload-excel/1")
	public ResponseEntity<List<FinalAction>> uploadExcel1(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("start: ");
		List<Customer> customers = new ArrayList<>();
		if (!file.isEmpty()) {
			try {
				InputStream inputStream = file.getInputStream();
				// Lấy file Excel từ request
				Workbook workbook = WorkbookFactory.create(inputStream);

				// Lấy sheet đầu tiên
				Sheet sheet = workbook.getSheetAt(0);
				System.out.println("check: ");
				// Lấy dữ liệu từ sheet
				for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
					// Kiểm tra xem ô đầu tiên của cột đó có giá trị là "id" hay không
					Cell cell = sheet.getRow(0).getCell(i);
					System.out.println("i " + i + " - cell: " + cell.getStringCellValue());
					if (cell.getStringCellValue().equals("id")) {
						// Tạo một mảng để chứa dữ liệu của cột đó
						Integer[] data = new Integer[sheet.getPhysicalNumberOfRows() - 1];

						// Lặp qua các hàng trong sheet, bắt đầu từ hàng thứ hai
						for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
							// Lấy dữ liệu từ ô thứ hai của cột đó
							Cell cell2 = sheet.getRow(j).getCell(i);

							// Thêm dữ liệu đó vào mảng
							data[j - 1] = (int) cell2.getNumericCellValue();
						}

						// In dữ liệu trong mảng
						for (Integer value : data) {
							Customer cus = new Customer();
							cus.setCustomerId((value));
							customers.add(cus);
						}

						// Thoát khỏi vòng lặp
						break;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error: " + e);
			}
		}
		for (Customer x : customers)
			System.out.println(x.getCustomerId());
		return new ResponseEntity<List<FinalAction>>(ruleSv2.ruleExcel(customers), HttpStatus.OK);
	}
	
	@PostMapping("/api/upload-excel")
	public ResponseEntity<List<Customer>> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
		System.out.println("start: ");
		List<Customer> customers = new ArrayList<>();
		if (!file.isEmpty()) {
			try {
				InputStream inputStream = file.getInputStream();
				// Lấy file Excel từ request
				Workbook workbook = WorkbookFactory.create(inputStream);

				// Lấy sheet đầu tiên
				Sheet sheet = workbook.getSheetAt(0);
				System.out.println("check: ");
				// Lấy dữ liệu từ sheet
				for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
					// Kiểm tra xem ô đầu tiên của cột đó có giá trị là "id" hay không
					Cell cell = sheet.getRow(0).getCell(i);
					System.out.println("i " + i + " - cell: " + cell.getStringCellValue());
					if (cell.getStringCellValue().equals("id")) {
						// Tạo một mảng để chứa dữ liệu của cột đó
						Integer[] data = new Integer[sheet.getPhysicalNumberOfRows() - 1];

						// Lặp qua các hàng trong sheet, bắt đầu từ hàng thứ hai
						for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
							// Lấy dữ liệu từ ô thứ hai của cột đó
							Cell cell2 = sheet.getRow(j).getCell(i);

							// Thêm dữ liệu đó vào mảng
							data[j - 1] = (int) cell2.getNumericCellValue();
						}

						// In dữ liệu trong mảng
						for (Integer value : data) {
							Customer cus = new Customer();
							cus.setCustomerId((value));
							customers.add(cus);
						}

						// Thoát khỏi vòng lặp
						break;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error: " + e);
			}
		}
		for (Customer x : customers)
			System.out.println(x.getCustomerId());
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	@GetMapping("/rule/EAV")
	public ResponseEntity<?> duyetTatCa() {
		System.out.println("dang duyet...");
		return new ResponseEntity<List<FinalAction>>(ruleSv2.getFinalActionAndDetails(), HttpStatus.OK);
	}

	@GetMapping("/rule")
	public ResponseEntity<?> duyetRule() {
		System.out.println("rule is being checked for all");
		if (ruleSv.recommend())
			return new ResponseEntity<String>("rule applied", HttpStatus.OK);
		else
			return new ResponseEntity<String>("no rule applied", HttpStatus.OK);

	}

//	@GetMapping("/rule/all")
//	public ResponseEntity<?> duyetRuleForAll() {
//		System.out.println("rule is being checked for all");
//		ruleSv.ruleForAll1Rule();
//		return new ResponseEntity<String>("done", HttpStatus.OK);
//
//	}
//
//	@GetMapping("/rule/forall")
//	public ResponseEntity<?> duyetRuleForAllCusRule() {
//		System.out.println("rule is being checked for all");
//		ruleSv.ruleForAll();
//		return new ResponseEntity<String>("done", HttpStatus.OK);
//
//	}

	@GetMapping("/rule/forall/why")
	public ResponseEntity<?> duyetRuleAndWhy() {
		System.out.println("rule is being checked for all");
		return new ResponseEntity<List<CustomerActionWhy>>(ruleSv2.CusAcCon(), HttpStatus.OK);

	}

}
