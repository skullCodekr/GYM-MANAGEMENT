// MemberDetails.js
import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function MemberDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [member, setMember] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [editData, setEditData] = useState({});
  const [photoFile, setPhotoFile] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchMember();
  }, [id]);

  const fetchMember = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/members/${id}`);
      setMember(res.data);
      setEditData({
        name: res.data.name || "",
        email: res.data.email || "",
        phone: res.data.phone || "",
        joiningDate: res.data.joiningDate || "",
        duration: res.data.duration || "",
        fee: res.data.fee || "",
      });
    } catch (err) {
      setError("Member not found");
    }
  };

  const handleUpdate = async () => {
    try {
      // 1️⃣ Update text fields
      await axios.put(`http://localhost:8080/api/members/${id}`, editData);

      // 2️⃣ Upload photo if selected
      if (photoFile) {
        const formData = new FormData();
        formData.append("file", photoFile);
        await axios.post(
          `http://localhost:8080/api/members/${id}/upload`,
          formData,
          { headers: { "Content-Type": "multipart/form-data" } },
        );
      }

      alert("Member updated!");
      setIsEditing(false);
      setPhotoFile(null);
      fetchMember(); // refresh data including photo
    } catch (err) {
      console.log(err);
      alert("Update failed");
    }
  };

  if (error)
    return <h2 style={{ color: "red", textAlign: "center" }}>{error}</h2>;
  if (!member)
    return <h2 style={{ textAlign: "center" }}>Loading member...</h2>;

  return (
    <div
      style={{
        padding: "20px",
        display: "flex",
        alignItems: "flex-start",
        gap: "20px",
      }}
    >
      {/* Profile Photo */}
      <div style={{ textAlign: "center" }}>
        {member.photoUrl && !photoFile && (
          <img
            src={`http://localhost:8080${member.photoUrl}`}
            alt="Member"
            style={{
              width: "120px",
              height: "120px",
              borderRadius: "50%",
              objectFit: "cover",
              marginBottom: "10px",
            }}
          />
        )}
        {photoFile && (
          <img
            src={URL.createObjectURL(photoFile)}
            alt="Preview"
            style={{
              width: "120px",
              height: "120px",
              borderRadius: "50%",
              objectFit: "cover",
              marginBottom: "10px",
            }}
          />
        )}
        {isEditing && (
          <input
            type="file"
            accept="image/*"
            onChange={(e) => setPhotoFile(e.target.files[0])}
          />
        )}
      </div>

      {/* Member Info */}
      <div style={{ flex: 1 }}>
        {["name", "email", "phone", "joiningDate", "duration", "fee"].map(
          (field) => (
            <p key={field}>
              <strong>{field.charAt(0).toUpperCase() + field.slice(1)}:</strong>{" "}
              {isEditing ? (
                <input
                  value={editData[field]}
                  onChange={(e) =>
                    setEditData({ ...editData, [field]: e.target.value })
                  }
                />
              ) : (
                member[field]
              )}
            </p>
          ),
        )}

        <p>
          <strong>Fee Status:</strong>{" "}
          <span style={{ color: member.feeStatus === "DUE" ? "red" : "green" }}>
            {member.feeStatus}
          </span>
        </p>

        <div style={{ marginTop: "10px" }}>
          {isEditing ? (
            <>
              <button onClick={handleUpdate} style={{ marginRight: "5px" }}>
                Save
              </button>
              <button onClick={() => setIsEditing(false)}>Cancel</button>
            </>
          ) : (
            <>
              <button
                onClick={() => setIsEditing(true)}
                style={{ marginRight: "5px" }}
              >
                Edit
              </button>
              <button onClick={() => navigate("/members")}>Back</button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default MemberDetails;
