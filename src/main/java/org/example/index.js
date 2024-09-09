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

document.addEventListener("DOMContentLoaded", () => {

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
            console.log(await fetch(`/fcgi-bin/webLab1.jar?x=${data.x}&y=${data.y}&r=${data.r}`));
        } catch (error) {
            console.error(error);
        }

    }

    document.querySelector(".check-point-form")
        .addEventListener("submit", handleCheckPoint);
});
