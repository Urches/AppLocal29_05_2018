$( document ).ready(()=>{
    let dropDownContent = $(".dropdown .dropdown-content")[0];

    $($(".dropdown .dropbtn")[0]).click(()=>{
        if($(dropDownContent).is(":visible")){
            console.log("hide!");
            $(dropDownContent).hide();
        } else {
            $(dropDownContent).show();
            console.log("show!");
        }
    });
    $("li", dropDownContent).toArray().forEach((li)=>{
        $($(li)[0]).click(()=> {
            $(dropDownContent).hide();
        });
    });
});