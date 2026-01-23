// pages/HomePage.js
import React, { useState } from "react";
import { Link } from "react-router-dom";

function HomePage({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div style={{ padding: "20px" }}>
      <h1>🔥 My Gym</h1>

      <div style={{ marginTop: "20px" }}>
        <h2>Welcome to My Gym!</h2>
        <p>Get fit. Stay strong. Achieve your goals.</p>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button
          onClick={() => {
            if (username === "admin" && password === "admin123") onLogin();
            else alert("Invalid credentials");
          }}
        >
          Login
        </button>
      </div>
    </div>
  );
}

export default HomePage;
