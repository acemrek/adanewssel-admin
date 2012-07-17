function setSelectedRowOfDataTable(elementInsideTd){
	$(elementInsideTd).closest("table").find("tr").removeClass("ui-state-highlight");
	$(elementInsideTd).closest("tr").addClass("ui-state-highlight");
}
