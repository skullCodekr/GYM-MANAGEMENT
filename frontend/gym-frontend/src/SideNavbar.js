import React, { useState } from "react";
import { Link } from "react-router-dom"; // Using Link from react-router-dom

const SideNavbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleSidebar = () => setIsOpen(!isOpen);

  return (
    <>
      {/* Hamburger icon */}
      <div
        onClick={toggleSidebar}
        style={{
          position: "fixed",
          top: "15px",
          left: "15px",
          fontSize: "28px",
          cursor: "pointer",
          zIndex: 1001,
          userSelect: "none",
        }}
        aria-label="Toggle navigation menu"
      >
        &#9776; {/* Unicode for 3 bars */}
      </div>

      {/* Sidebar */}
      <div
        style={{
          position: "fixed",
          top: 0,
          left: isOpen ? 0 : "-220px", // slide in/out
          width: "220px",
          height: "100vh",
          backgroundColor: "#222",
          color: "white",
          paddingTop: "60px",
          transition: "left 0.3s ease",
          zIndex: 1000,
          boxShadow: isOpen ? "2px 0 5px rgba(0,0,0,0.3)" : "none",
        }}
      >
        <nav
          style={{
            display: "flex",
            flexDirection: "column",
            gap: "20px",
            paddingLeft: "20px",
          }}
        >
          {/* Using Link for React Router navigation */}
          <Link
            to="/login"
            style={linkStyle}
            onClick={() => setIsOpen(false)} // Close sidebar after clicking
          >
            User Login
          </Link>

          <Link
            to="/admin" // Correct path for Admin Login
            style={linkStyle}
            onClick={() => setIsOpen(false)} // Close sidebar after clicking
          >
            Admin Login
          </Link>
        </nav>
      </div>

      {/* Overlay to close sidebar when clicking outside */}
      {isOpen && (
        <div
          onClick={toggleSidebar}
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100vw",
            height: "100vh",
            backgroundColor: "rgba(0,0,0,0.3)",
            zIndex: 999,
          }}
        />
      )}
    </>
  );
};

// External style for links
const linkStyle = {
  color: "white",
  textDecoration: "none",
  fontSize: "18px",
  padding: "10px 0",
  borderBottom: "1px solid #444",
};

export default SideNavbar; // Correct export to make it available to App.js
