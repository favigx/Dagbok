let currentDate = document.getElementById("currentDate")

if(currentDate){
    currentDate.textContent = new Date().toLocaleDateString();
}