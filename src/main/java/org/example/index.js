"use strict";

function validateInput({x, y, r}) {

    if (x === "" || y === "" || r === "") {
        alert("Please fill all fields");
        return false;
    }

    if (Number.isNaN(x) || Number.isNaN(y) || Number.isNaN(r)) {
        alert("Please enter a number");
        return false;
    }

    if (x < -5 || x > 3) {
        alert("Please enter a number between -3 and 5");
        return false;
    }

    if (y < -5 || y > 3) {
        alert("Please enter a number between -3 and 5");
        return false;
    }

    if (r < 1 || r > 3) {
        alert("Please enter a number between 1 and 5");
        return false;
    }

    return true;
}

document.addEventListener("DOMContentLoaded", (

) => {
    loadTableData();

    async function handleCheckPoint(event) {

        event.preventDefault();
        const formData = new FormData(event.target);
        console.log(formData)
        const data = {
            x: +formData.get("x"),
            y: +formData.get("y"),
            r: +formData.get("r")
        };
        console.log(data)
        if (!validateInput(data)) return;
        try {
            const response = await fetch(`/fcgi-bin/webLab1.jar?x=${data.x}&y=${data.y}&r=${data.r}`);
            if (!response.ok) return;
            const {
                hit,
                startTime,
                executionTime
            } = await response.json();
            const currentOutput = JSON.parse(localStorage.getItem("current-output")) || [];
            currentOutput.push({...data, hit, startTime, executionTime});
            localStorage.setItem("current-output", JSON.stringify(currentOutput));
            loadTableData();
        } catch (error) {

            console.error(error);
        }

    }

    document.querySelector(".check-point-form")
        .addEventListener("submit", handleCheckPoint);
});

function loadTableData() {
    const currentOutput = JSON.parse(localStorage.getItem("current-output")) || [];

    const tbody = document.querySelector("tbody.output");

    tbody.innerHTML = "";

    currentOutput.forEach(item => {
        const row = document.createElement("tr");

        row.innerHTML = `
                    <td>${item.x}</td>
                    <td>${item.y}</td>
                    <td>${item.r}</td>
                    <td>${item.hit}</td>
                    <td>${item.startTime}</td>
                    <td>${item.executionTime}</td>
                `;

        tbody.appendChild(row);
    });
}