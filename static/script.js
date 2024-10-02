localStorage.clear();

function validateInput({x, y, r}) {
    console.log("Started validation")
    if (x == null) return false;
    if (y == null) return false;
    if (r == null) return false;
    return true;
}

document.querySelectorAll(".x-input").forEach((item)=>{
    item.addEventListener('change', ()=>{
        loadX()
    })
})

document.getElementById("submit").addEventListener("click",
    () => {
        console.log("Started script")
        const x = loadX();
        const y = loadY();
        const r = loadR();

        if (!validateInput({x, y, r})) {
            return
        }

        getResponce({x, y, r})

        // const  responce = getResponce({x, y, r})
        updateTable({x, y, r});
        console.log("Finished script")
    })


async function getResponce({x, y, r}) {

    try {
        const response = await fetch(`/fcgi-bin/server.jar`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                x, y, r
            })
        });
        if (!response.ok) {
            console.log(response)
            console.log(response.body)
            return
        }
        const {
            hit,
            startTime,
            executionTime
        } = await response.json();
        const currentOutput = JSON.parse(localStorage.getItem("current-output")) || [];
        currentOutput.push({x, y, r, hit, startTime, executionTime});
        localStorage.setItem("current-output", JSON.stringify(currentOutput));
        updateTable();
    } catch (error) {

        console.error(error);
    }

}

function loadX() {
    const checkedValues = []
    document.querySelectorAll('#x-input-form input[type=checkbox]')
        .forEach((checkbox) => {
            if (checkbox.checked) {
                checkedValues.push(checkbox.value)
                if (checkedValues.length>1){
                    checkbox.checked=false;
                }
            }
        });

    return parseFloat(checkedValues[0]);

}

function loadY() {

    const textInput = document.getElementById("y-input").value;

    const floatValue = parseFloat(textInput);

    if (isNaN(floatValue)) {
        alert("Please fill Y")
        return
    }
    if (!((-5 <= floatValue) && (floatValue <= 3))) {

        alert("You wrote incorrect value")
        return;
    }
    return floatValue;
}

function loadR() {
    var r = null;
    document.querySelectorAll("input[name=r]")
        .forEach((radio) => {
            if (radio.checked) {
                r = parseFloat(radio.value);
            }
        })
    if (r == null) {
        alert("Select value for R")
    } else {
        return r
    }

}


function updateTable() {
    const currentOutput = JSON.parse(localStorage.getItem("current-output")) || [];

    const tbody = document.querySelector("tbody.output");

    tbody.innerHTML = "";

    currentOutput.forEach(item => {
        const row = document.createElement("tr");
        console.log(item.startTime)
        console.log(item.executionTime)
        row.innerHTML = `
                    <td>${item.x}</td>
                    <td>${item.y}</td>
                    <td>${item.r}</td>
                    <td>${item.hit}</td>
                    <td>${item.startTime.toString()}</td>
                    <td>${item.executionTime.toString()}</td>
                `;
        tbody.appendChild(row);
    });
}