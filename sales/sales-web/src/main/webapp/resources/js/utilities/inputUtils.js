/**
 * This file contains a set of input utilities.
 */

function contentToUpperCase(tag) {
	tag.value = tag.value.toUpperCase();
}

function deleteNonAlphaContent(tag, exceptions) {
	var content = tag.value;
	var newContent = "";
	var character = '';
	if(validContentNTrim(tag)) {
		return;
	}
	for(i = 0; i < content.length; i++){
		character = content.charAt(i);
		if((character >= 'a' && 
				character <= 'z') ||
				(character >= 'A' && 
				character <= 'Z') ||
				exceptions.includes(character)){
			newContent += character;
		}
	}
	tag.value = newContent;
}

function deleteNonIntegerContent(tag, exceptions) {
	var content = tag.value;
	var newContent = "";
	var character = '';
	if(validContentNTrim(tag)) {
		return;
	}
	for(i = 0; i < content.length; i++){
		character = content.charAt(i);
		if((character >= '0' && 
				character <= '9') ||
				exceptions.includes(character)){
			newContent += character;
		}
	}
	tag.value = newContent;
}

function deleteExtraBlankSpaces(tag){
	var content = tag.value.trim();
	var newContent = "";
	var character = '';
	var priorCharacter = '';
	if(validContentNTrim(tag)) {
		return;
	}
	for(i = 1; i < content.length; i++){
		character = content.charAt(i);
		priorCharacter = content.charAt(i - 1);
		if(character !== ' ' || 
				priorCharacter !== ' '){
			newContent += character;
		}
	}
	if(content.length > 1){
		tag.value = content.charAt(0) + newContent;
	}
}

function validContentNTrim(tag) {
	var content = tag.value;
	if(content === undefined || typeof content !== "string"){
		return false;
	}
	true;
}