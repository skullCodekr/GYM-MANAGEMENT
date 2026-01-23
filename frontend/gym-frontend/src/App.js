// App.js
import React, { useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  Navigate,
  useNavigate,
} from "react-router-dom";
import HomePage from "./HomePage";
import About from "./about";
import Contact from "./contactUs";
import Help from "./help";
import MemberDetails from "./memberDetails";

// ===== Admin Dashboard Component =====
function AdminDashboard({ setIsAdmin }) {
  const navigate = useNavigate();

  const viewDetails = (id) => {
    navigate(`/member/${id}`);
  };
  const [members, setMembers] = useState([]);
  const [showMembers, setShowMembers] = useState(false);

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [editId, setEditId] = useState(null);

  // Fetch members
  const fetchMembers = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/members");
      const data = await res.json();
      setMembers(data);
    } catch (err) {
      console.log("Error fetching members");
    }
  };

  useEffect(() => {
    fetchMembers();
  }, []);

  // CRUD Functions
  const addMember = async () => {
    if (!name || !email || !phone) return alert("Fill all fields!");
    const member = { name, email, phone };
    try {
      const res = await fetch("http://localhost:8080/api/members", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(member),
      });
      if (res.ok) {
        alert("Member Added!");
        fetchMembers();
        setName("");
        setEmail("");
        setPhone("");
      }
    } catch {
      alert("Backend error");
    }
  };

  const deleteMember = async (id) => {
    await fetch(`http://localhost:8080/api/members/${id}`, {
      method: "DELETE",
    });
    fetchMembers();
  };

  const startUpdate = (m) => {
    setEditId(m.id);
    setName(m.name);
    setEmail(m.email);
    setPhone(m.phone);
  };
  const updateMember = async () => {
    if (!name || !email || !phone) return alert("Fill all fields!");
    await fetch(`http://localhost:8080/api/members/${editId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, phone }),
    });
    alert("Updated!");
    setEditId(null);
    setName("");
    setEmail("");
    setPhone("");
    fetchMembers();
  };

  return (
    <div style={{ padding: "20px" }}>
      <button
        onClick={() => setIsAdmin(false)}
        style={{ float: "right", marginBottom: "20px" }}
      >
        Logout
      </button>
      <h1>Gym Members</h1>

      {/* Add / Update Form */}
      <div>
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="text"
          placeholder="Phone"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
        />
        {editId ? (
          <button onClick={updateMember}>Update Member</button>
        ) : (
          <button onClick={addMember}>Add Member</button>
        )}
      </div>

      <br />
      <button onClick={() => setShowMembers(!showMembers)}>
        {showMembers ? "Hide Members" : "Show Members"}
      </button>
      <br />
      <br />

      {showMembers && (
        <table border="1" cellPadding="10">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Delete</th>
              <th>Edit</th>
            </tr>
          </thead>
          <tbody>
            {members.map((m) => (
              <tr key={m.id}>
                <td>{m.name}</td>
                <td>{m.email}</td>
                <td>{m.phone}</td>
                <td>
                  <button onClick={() => deleteMember(m.id)}>Delete</button>
                </td>
                <td>
                  <button onClick={() => startUpdate(m)}>Edit</button>
                </td>
                <td>
                  <button onClick={() => viewDetails(m.id)}>
                    View Details
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

// ===== Main App =====
function App() {
  const [isAdmin, setIsAdmin] = useState(false);

  return (
    <Router>
      <nav
        style={{
          padding: "10px",
          display: "flex",
          justifyContent: "flex-end",
          gap: "20px",
          borderBottom: "1px solid #ccc",
        }}
      >
        <Link to="/">Home</Link> | <Link to="/about">About Us</Link> |{" "}
        <Link to="/contactUs">Contact Us</Link> | <Link to="/help">Help</Link>
      </nav>

      <Routes>
        <Route
          path="/"
          element={
            !isAdmin ? (
              <HomePage onLogin={() => setIsAdmin(true)} />
            ) : (
              <Navigate to="/admin" />
            )
          }
        />
        <Route path="/about" element={<About />} />
        <Route path="/contactUs" element={<Contact />} />
        <Route path="/help" element={<Help />} />
        <Route path="/member/ :id" element={<MemberDetails />} />

        <Route
          path="/admin"
          element={
            isAdmin ? (
              <AdminDashboard setIsAdmin={setIsAdmin} />
            ) : (
              <Navigate to="/" />
            )
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
