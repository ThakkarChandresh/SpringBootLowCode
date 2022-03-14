function generateForm(formDetail){
	var form = document.createElement('form');
	form.className = "forms-sample";
	form.method = "post";
	form.action = "#";
	
	for(var i=0;i<formDetail.length;i++)
	{
		if(formDetail[i].fieldType === 'text-area'){
			var newElement = document.createElement('textarea');
			newElement.name = camelize(formDetail[i].fieldName);
			newElement.id = camelize(formDetail[i].fieldName);
			newElement.className = "form-control";
			
			form.insertAdjacentHTML('beforeend', '<div class="form-group"><label for="'+formDetail[i].fieldName+'">'+formDetail[i].fieldName+'</label>'+newElement.outerHTML+'</div>');
		}else if(formDetail[i].fieldType === 'radio' || formDetail[i].fieldType === 'checkbox'){
			var elementList="";
			var subInputs = formDetail[i].options;
			
			for (var j=0;j<subInputs.length;j++){
				var newSubElement = document.createElement('input');
				
				newSubElement.type = formDetail[i].fieldType;
				newSubElement.name = camelize(formDetail[i].fieldName);
				newSubElement.className = "form-check-input";
				
				newSubElement.id = camelize(subInputs[j].label);
				newSubElement.value = subInputs[j].value;
				
				elementList += '<div class="form-check">'+newSubElement.outerHTML+'<label class="form-check-label" for="'+subInputs[j].label+'">'+subInputs[j].label+'</label></div>';
			}
			form.insertAdjacentHTML('beforeend', '<div class="form-group"><label for="'+formDetail[i].fieldName+'">'+formDetail[i].fieldName+'</label>'+elementList+'</div>');
	}else if(formDetail[i].fieldType === 'dropdown'){
		var newElement = document.createElement('select');
		newElement.className = "form-control";
		newElement.name = camelize(formDetail[i].fieldName);
		newElement.id = camelize(formDetail[i].fieldName);
		
		var subInputs = formDetail[i].options;
		
		for (var j=0;j<subInputs.length;j++){
			var opt = document.createElement('option');
			opt.value = subInputs[j].value;
			opt.text = subInputs[j].label;
			
			newElement.add(opt);
		}
		form.insertAdjacentHTML('beforeend', '<div class="form-group"><label for="'+formDetail[i].fieldName+'">'+formDetail[i].fieldName+'</label>'+newElement.outerHTML+'</div>');
	}else{
		var newElement = document.createElement('input');
		newElement.type = formDetail[i].fieldType;
		newElement.className = "form-control";
		
		newElement.name = camelize(formDetail[i].fieldName);
		newElement.id = camelize(formDetail[i].fieldName);
		
		form.insertAdjacentHTML('beforeend','<div class="form-group"><label for="'+formDetail[i].fieldName+'">'+formDetail[i].fieldName+'</label>'+newElement.outerHTML+'</div>');
	}
}
	return form;
}

function camelize(str) {
	  return str.replace(/(?:^\w|[A-Z]|\b\w)/g, function(word, index) {
	    return index === 0 ? word.toLowerCase() : word.toUpperCase();
	  }).replace(/\s+/g, '');
	}
