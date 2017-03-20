/**
 * @Author David Israel Garcia Alcazar
 */

/**
 * This function is executed every 5 seconds
 * and animates the list of banks.
 */
window.setInterval(function(){
	banks = document.getElementById('banksList');
	banksNumber = banks.childElementCount;
	banksList = banks.childNodes;
	firstBank = banksList.item(0);
	banks.removeChild(firstBank);
	banks.appendChild(firstBank);
}, 700);

/**
 * This function generates a click event over the show 
 * AforeChoosingModal button, in order to launch the modal.
 * @returns undefined
 */
function showAforeChoosingModal() {
	var showAforeChoosingModalBtn = document.getElementById("aforeChoosingModalShowBtn");
	if(showAforeChoosingModalBtn) {
		showAforeChoosingModalBtn.click();
	}
}