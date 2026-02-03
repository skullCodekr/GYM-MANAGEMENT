let API_BASE_URL = "";

// Agar React app PC pe chal rha hai
if (window.location.hostname === "localhost") {
  API_BASE_URL = "http://localhost:8080";
} else {
  // Phone se access kar rahe ho, yaha apne PC ka LAN IP dal do
  API_BASE_URL = "http://192.168.X.X:8080"; // replace X.X with your PC IP
}

export { API_BASE_URL };
