$(document).ready(function () {
    $('#search-form').submit(function (event) {
        event.preventDefault(); // Ngăn chặn gửi lại trang

        let seat = parseInt(document.getElementById("seat").value);
        let carLocation = document.getElementById("location").value;
        let pickUpDate = document.getElementById("pick-up-date").value;
        let returnDate = document.getElementById("return-date").value;

        let searchData = {
            seat: seat,
            carLocation: carLocation,
            startDate: new Date(pickUpDate),
            endDate: new Date(returnDate)
        };
        $.ajax({
            type: 'POST',
            url: '/customerRes/search-car',
            data: JSON.stringify(searchData),
            contentType: 'application/json',
            success: function (data) {
                // Xử lý kết quả tìm kiếm
                console.log(data);
                let temp = "";
                for (let i = 0; i < data.length; i++) {
                    temp += getCarDTO(data[i]);
                }
                $('#search-result').html(temp);
            },
            error: function () {
                alert('Error searching for cars. Please try again later.');
            }
        });
    });
});


function successHandler() {
    $.ajax({
        type: "GET",
        //tên API
        url: "/customerRes",
        //xử lý khi thành công
        success: function (carDTO) {
            // hien thi danh sach o day
            let content = "";
            debugger
            for (let i = 0; i < carDTO.length; i++) {
                content += getCarDTO(carDTO[i]);
            }
            debugger
            document.getElementById('search-result').innerHTML = content;
        }
    });
}

function getCarDTO(carDTO) {
    return `
            <div class="col-md-4 col-lg-3 mb-3 mb-md-0">
                <div class="card text-black" style="background-color: #f5f4f4; border-radius: 5%; margin-top: 15px">
                    <img src="${carDTO.url}" class="card-img-top" alt="..."
                         style="width: 100%; height: 17rem; border-radius: 5%">
                            <div class="card-body p-4">
                                <h4 class="card-title">
                                    <a th:data-bs-toggle="modal" th:data-bs-target="'#carDetailModal' + ${carDTO.id}">
                                        ${carDTO.brand} ${carDTO.model}
                                    </a>
                                </h4>
                                <div th:include="customer/modal :: carDetailModal(${carDTO}, ${carDTO.id})"></div>
                                <div class="card-text ">
                                    <ul class="d-flex list-unstyled">
                                        <li class="rental-list"><img src="/images/dot.png" class="px-3" alt="#"></li>
                                        <li class="rental-list flex-fill"><span>${carDTO.seat}</span> Seats</li>
                                        <li class="rental-list"><img src="/images/dot.png" class="px-3" alt="#"></li>
                                        <li class="rental-list flex-fill"><span>${carDTO.carLocation}</span></li>
                                    </ul>
                                </div>
                                <hr>
                                <div class="d-flex justify-content-between">
                                    <h3 class="pt-2" style="font-size: 1.5rem">
                                        <span class="rental-price">${formatNumberWithCommas(carDTO.rentPrice)} VND/Day</span>
                                    </h3>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                            th:data-bs-target="'#carRentModal' + ${carDTO.id}">
                                        rent now
                                    </button>
                                    <div th:include="customer/modal :: carRentModal(${carDTO}, ${carDTO.id})"></div>
                                </div>
                            </div>
                </div>
            </div>  `;
}

function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}