package com.ac.newsadmin.ui.tools.configTools;

import org.primefaces.event.FlowEvent;

public class PhotoSizeConfigTool {
	
	private int currentLevel = 1;
	
	public void save(){
		
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	public String  onFlowProcess(FlowEvent event){
		return event.getNewStep();  
	}
	
}	
