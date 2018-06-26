package controller;

import java.util.ArrayList;
import java.util.List;

import modle.FileMaster;

public class Poster {
	public int counter;
	public List<FileMaster> files;

public	Poster()
	{
		counter = 1;
		files = new ArrayList<FileMaster>();
	}
}
