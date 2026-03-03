script.js
document.getElementById("regForm").addEventListener("submit", function(e) {

  e.preventDefault(); // stop form submission

  let valid = true;

  // Get values
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const phone = document.getElementById("phone").value.trim();
  const gender = document.querySelector('input[name="gender"]:checked');
  const course = document.getElementById("course").value;
  const password = document.getElementById("password").value;

  // Clear previous errors
  document.querySelectorAll(".error").forEach(e => e.textContent = "");

  // Name validation
  if (name === "") {
    document.getElementById("nameError").textContent = "Name is required";
    valid = false;
  }

  // Email validation
  const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
  if (email === "") {
    document.getElementById("emailError").textContent = "Email is required";
    valid = false;
  } else if (!emailPattern.test(email)) {
    document.getElementById("emailError").textContent = "Invalid email format";
    valid = false;
  }

  // Phone validation
  const phonePattern = /^[0-9]{10}$/;
  if (phone === "") {
    document.getElementById("phoneError").textContent = "Phone is required";
    valid = false;
  } else if (!phonePattern.test(phone)) {
    document.getElementById("phoneError").textContent = "Phone must be 10 digits";
    valid = false;
  }

  // Gender validation
  if (!gender) {
    document.getElementById("genderError").textContent = "Select gender";
    valid = false;
  }

  // Course validation
  if (course === "") {
    document.getElementById("courseError").textContent = "Select course";
    valid = false;
  }

  // Password validation
  const passPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

  if (password === "") {
    document.getElementById("passwordError").textContent = "Password is required";
    valid = false;
  } else if (!passPattern.test(password)) {
    document.getElementById("passwordError").textContent =
      "Min 6 chars, include letters & numbers";
    valid = false;
  }

  // Success
  if (valid) {
    alert("Registration Successful!");
  }

});