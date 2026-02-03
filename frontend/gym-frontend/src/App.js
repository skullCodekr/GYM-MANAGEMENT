// App.js
import React, { useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
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
  return (
    <Router>
      {/* Show SideNavbar only if not admin */}
      {!isAdmin && <SideNavbar />}

      {/* Top nav links for normal users */}
      {!isAdmin && (
        <nav
          style={{
            padding: "10px",
            display: "flex",
            justifyContent: "flex-end",
            gap: "20px",
            borderBottom: "1px solid #ccc",
          }}
        >
          <a href="/">Home</a> | <a href="/about">About Us</a> |{" "}
          <a href="/contactUs">Contact Us</a> | <a href="/help">Help</a> |{" "}
          <a href="/offers">Offers</a>
        </nav>
      )}

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
