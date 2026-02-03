import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const MembersPage = () => {
  const [members, setMembers] = useState([]);
  const [newMember, setNewMember] = useState({
    name: "",
    email: "",
    phone: "",
  });

  const [editMember, setEditMember] = useState(null);
  const navigate = useNavigate();

  const API = "http://localhost:8080/api/members";

  // ===================== FETCH ALL =====================
  const loadMembers = async () => {
    try {
      const res = await axios.get(API);
      setMembers(res.data);
    } catch (err) {
      console.log("Error fetching members:", err);
    }
  };

  useEffect(() => {
    loadMembers();
  }, []);

  // ===================== ADD MEMBER =====================
  const addMember = async () => {
    if (!newMember.name.trim() || !newMember.phone.trim()) {
      alert("Fill all fields!");
      return;
    }

    try {
      await axios.post(API, newMember);
      alert("Member Added!");
      loadMembers();
      setNewMember({ name: "", email: "", phone: "" });
    } catch (err) {
      console.log("Backend error detail:", err);
      if (err.response && typeof err.response.data === "string") {
        alert(err.response.data);
        return;
      }
      if (err.response && err.response.data && err.response.data.message) {
        alert(err.response.data.message);
        return;
      }
      alert(err.message || "Something went wrong!");
    }
  };

  // ===================== DELETE MEMBER =====================
  const deleteMember = async (id) => {
    try {
      await axios.delete(`${API}/${id}`);
      alert("Member deleted!");
      loadMembers();
    } catch (err) {
      console.log("Error deleting member:", err);
    }
  };

  // ===================== UPDATE MEMBER =====================
  const updateMember = async () => {
    try {
      await axios.put(`${API}/${editMember.id}`, editMember);
      alert("Member updated!");
      setEditMember(null);
      loadMembers();
    } catch (err) {
      if (err.response) {
        alert(err.response.data);
      } else {
        alert(err.message);
      }
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Gym Members</h2>

      {/* ============ ADD MEMBER FORM ============ */}
      <h3>Add Member</h3>
      <input
        placeholder="Name"
        value={newMember.name}
        onChange={(e) => setNewMember({ ...newMember, name: e.target.value })}
      />
      <input
        placeholder="Email"
        value={newMember.email}
        onChange={(e) => setNewMember({ ...newMember, email: e.target.value })}
      />
      <input
        placeholder="Phone"
        value={newMember.phone}
        onChange={(e) => setNewMember({ ...newMember, phone: e.target.value })}
      />
      <button onClick={addMember}>Add</button>

      <br />
      <br />

      {/* ============ MEMBERS TABLE ============ */}
      <h3>All Members</h3>

      {members.length === 0 ? (
        <p>No members found</p>
      ) : (
        <table border="1" cellPadding="10">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Phone</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {members.map((m) => (
              <tr key={m.id}>
                <td>{m.id}</td>
                <td>{m.name}</td>
                <td>{m.email}</td>
                <td>{m.phone}</td>
                <td>
                  <button
                    style={{ marginRight: "1px" }}
                    onClick={() => setEditMember(m)}
                  >
                    Edit
                  </button>
                  <button
                    style={{ marginRight: "1px" }}
                    onClick={() => deleteMember(m.id)}
                  >
                    Delete
                  </button>
                  <button onClick={() => navigate(`/members/${m.id}`)}>
                    View Details
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* ============ UPDATE POPUP FORM ============ */}
      {editMember && (
        <div
          style={{
            marginTop: "20px",
            padding: "20px",
            border: "2px solid gray",
            width: "300px",
          }}
        >
          <h3>Edit Member</h3>

          <input
            value={editMember.name}
            onChange={(e) =>
              setEditMember({ ...editMember, name: e.target.value })
            }
          />
          <input
            value={editMember.email}
            onChange={(e) =>
              setEditMember({ ...editMember, email: e.target.value })
            }
          />
          <input
            value={editMember.phone}
            onChange={(e) =>
              setEditMember({ ...editMember, phone: e.target.value })
            }
          />

          <button onClick={updateMember}>Update</button>
          <button onClick={() => setEditMember(null)}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default MembersPage;
