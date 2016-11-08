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
