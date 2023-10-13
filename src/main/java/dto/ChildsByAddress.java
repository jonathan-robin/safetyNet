package dto;

import java.util.List;

public class ChildsByAddress {

	List<HomeByAddress> childs; 
	List<HomeByAddress> adults;
	
	public List<HomeByAddress> getChilds(){
		return this.childs;
	}
	
	public List<HomeByAddress> getAdults(){
		return this.adults;
	}
	
	public void setChilds(List<HomeByAddress> childs) {
		this.childs = childs;
	}
	
	public void setAdults(List<HomeByAddress> parents) {
		this.adults = parents;
	}

	
}
