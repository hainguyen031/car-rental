// $("#btnConfirm").click(()=>{
//     console.log("hello")
//
//     let cusId = 1;
//     let carId = $('#carId').val();
//     let cccd = $('#cccd').val();
//     let gplx = $('#gplx').val();
//     let pickupLocation = $('#pickupLocation1').val();
//     let startDateStr = $('#startDate').val();
//     let endDateStr = $('#endDate').val();
//         console.log(document.getElementById("cccd").value);
//                console.log(document.getElementById("gplx").value);
//                console.log(cccd);
// });

$(document).ready(()=>{
    let temp = document.getElementById("btnConfirm");
    temp.addEventListener("click",()=>{
        console.log(document.getElementById("cccd").value);
        console.log(document.getElementById("gplx").value);
    })
})

function handleClick() {

    console.log("hello")

    let cusId = 1;
    let carId = $('#carId').val();
    let cccd = $('#cccd').val();
    let gplx = $('#gplx').val();
    let pickupLocation = $('#pickupLocation1').val();
    let startDateStr = $('#startDate').val();
    let endDateStr = $('#endDate').val();

    let rentData = {
        carId : carId,
        customerId : cusId,
        cccd : cccd,
        gplx : gplx,
        pickupLocation : pickupLocation,
        startDate : new Date(startDateStr),
        endDate : new Date(endDateStr)
    };
    console.log(rentData);
    $.ajax({
        type: 'POST',
        url: '/customerRes/rent-car',
        data: JSON.stringify(rentData),
        contentType: 'application/json',
        // dataType: 'json',
        success: function (data) {
            console.log(data);
        },
        error: function () {
            alert('Error rent cars. Please try again later.');
        }
    });
}


