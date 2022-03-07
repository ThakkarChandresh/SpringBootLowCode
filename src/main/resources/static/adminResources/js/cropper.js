var rawImg = "";
var imgFormat = "";

// Read file functionality
function readFile(input) {  
  if (input.files && input.files[0]) {
	  
    
    console.log(input.files[0].type);
    
    var file_size = input.files[0].size/1024/1024;
    
   
    
    if (file_size >= 5) {
      alert('File is over 5MB.');
      return;
    }
    
    var reader = new FileReader();
    reader.onload = function(e) {
      // File is read okay
      rawImg = e.target.result;
      $(".upload-demo").addClass("ready");
      $('#addProfilePicture').modal('show');
      
      $('#addProfilePicture').on('shown.bs.modal', function(){ 
    	  bindCroppie();
      });
         
    };
    reader.readAsDataURL(input.files[0]);
  }
}

// Initialize croppie canvas object
$uploadCrop = $("#upload-demo").croppie({
  viewport: {
    width: 220,
    height: 220,
    type: 'circle'
  },
  enforceBoundary: false,
  enableExif: true
});

// Bind Croppie to the image
function bindCroppie() {
  $uploadCrop
    .croppie("bind", {
      url: rawImg
    })
    .then(function() {
      useCroppie();
    });
}

$($uploadCrop).on("click", function(e) {
  useCroppie();
});

function useCroppie() {
   $uploadCrop
    .croppie("result", {
      type: "base64",
      format: "png",
      size: "original"
    })
    .then(function(resp) {
    	$( "#crop-button" ).click(function() {
    		 $('#item-img-output').attr('src', resp);
    		 $('#item-img-output').css('display', 'block');
    		 $('.modal-footer').show();
    		});
    });
}

   
$(".item-img").on("change", function() {
  readFile(this);
});


$('#addProfilePicture').on('hidden.bs.modal', function(){
	$('.cr-image').removeAttr('src');
	$('.cr-image').removeAttr('style');
	$('.cr-overlay').removeAttr('style');
	
	$("#item-img-output").css("display", "none");
	
	$('#item-img-output').attr('src','');
	
	$('.modal-footer').hide();
	
	$("#file-input").val(null);
	
});
