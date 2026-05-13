// App.js
import React, { useState } from "react";
import {
  HashRouter as Router,
  Routes,
  Route,
  Navigate,
  Link,
} from "react-router-dom";
import HomePage from "./HomePage";
import About from "./about";
import Contact from "./contactUs";
import Help from "./help";
import MembersPage from "./pages/MemberPage";
import MemberDetails from "./memberDetails";
import SideNavbar from "./SideNavbar";
import AdminLoginAndDashboard from "./AdminLoginAndDashboard";
import OffersPage from "./OffersPage";

function App() {
  const [isAdmin, setIsAdmin] = useState(false);

  // Nav link style
  const linkStyle = {
    textDecoration: "none",
    color: "#333",
    transition: "color 0.2s",
  };

  const linkHover = (e) => (e.target.style.color = "#007bff");
  const linkLeave = (e) => (e.target.style.color = "#333");

  return (
    <Router>
      {/* Top nav with hamburger left, links right */}
      {!isAdmin && (
        <>
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center", // vertically center icon and links
              padding: "10px 20px", // adjust vertical padding for better spacing
              fontFamily: "Arial, sans-serif",
              fontWeight: 500,
            }}
          >
            {/* Wrap SideNavbar in a div with some margin */}
            <div style={{ marginTop: "4px", marginLeft: "4px" }}>
              <SideNavbar />
            </div>

            <div style={{ display: "flex", gap: "20px" }}>
              <Link
                to="/"
                style={linkStyle}
                onMouseEnter={linkHover}
                onMouseLeave={linkLeave}
              >
                Home
              </Link>
              <Link
                to="/about"
                style={linkStyle}
                onMouseEnter={linkHover}
                onMouseLeave={linkLeave}
              >
                About Us
              </Link>
              <Link
                to="/contactUs"
                style={linkStyle}
                onMouseEnter={linkHover}
                onMouseLeave={linkLeave}
              >
                Contact Us
              </Link>
              <Link
                to="/help"
                style={linkStyle}
                onMouseEnter={linkHover}
                onMouseLeave={linkLeave}
              >
                Help
              </Link>
              <Link
                to="/offers"
                style={linkStyle}
                onMouseEnter={linkHover}
                onMouseLeave={linkLeave}
              >
                Offers
              </Link>
            </div>
          </div>

          {/* Thin line below nav */}
          <div style={{ borderBottom: "1px solid #ccc" }}></div>
        </>
      )}

      {/* Show SideNavbar only if not admin (if needed elsewhere) */}
      {!isAdmin && <SideNavbar />}

      <Routes>
        {/* HomePage shows welcome message, admin login is separate */}
        <Route
          path="/"
          element={!isAdmin ? <HomePage /> : <Navigate to="/admin" />}
        />
        <Route path="/about" element={<About />} />
        <Route path="/contactUs" element={<Contact />} />
        <Route path="/help" element={<Help />} />
        <Route path="/offers" element={<OffersPage />} />

        {/* Members CRUD */}
        <Route path="/members/:id" element={<MemberDetails />} />
        <Route path="/members" element={<MembersPage />} />

        {/* Admin login & dashboard */}
        <Route
          path="/admin"
          element={<AdminLoginAndDashboard setIsAdmin={setIsAdmin} />}
        />
      </Routes>
    </Router>
  );
}

export default App;
