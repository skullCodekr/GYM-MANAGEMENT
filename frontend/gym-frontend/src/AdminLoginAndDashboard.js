// AdminLoginAndDashboard.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const AdminLoginAndDashboard = ({ setIsAdmin }) => {
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // ================= LOGIN =================
  const handleLogin = () => {
    if (username === "admin" && password === "admin123") {
      setIsAuthenticated(true);
      setIsAdmin(true);

      // Login ke baad members page pe redirect
      navigate("/members");
    } else {
      alert("Invalid Credentials");
    }
  };

  // ================= LOGOUT =================
  const handleLogout = () => {
    setIsAuthenticated(false);
    setIsAdmin(false);
    navigate("/"); // logout pe home page ya login page
  };

  return (
    <div style={{ padding: "20px" }}>
      {isAuthenticated ? (
        <>
          <button
            onClick={handleLogout}
            style={{ float: "right", marginBottom: "20px" }}
          >
            Logout
          </button>
          <h2>Welcome, Admin!</h2>
          <p>Use the Members page to manage gym members.</p>
        </>
      ) : (
        <div>
          <h2>Admin Login</h2>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <br />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <br />
          <button onClick={handleLogin}>Login</button>
        </div>
      )}
    </div>
  );
};

export default AdminLoginAndDashboard;
