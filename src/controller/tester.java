package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamTokenizer;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modle.FileMaster;
import modle.hibernateSearchEngine;



public class tester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	String str ="ziv";
	String str1 = "Ziv";
	//str1 = str1.toLowerCase();
		if(str.equals(str1.toLowerCase()))
			System.out.println("true");
		else
			System.out.println(str1 + str);
	
	}
}
