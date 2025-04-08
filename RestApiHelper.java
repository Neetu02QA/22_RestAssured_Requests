package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestApiHelper {

	public static RequestSpecification getBasicAuth() {
		return RestAssured.given().auth().basic("Numpy@gmail.com", "userAPI").contentType(ContentType.JSON)
				.baseUri("https://userapi-8877aadaae71.herokuapp.com/uap");
		
	}

	

}
