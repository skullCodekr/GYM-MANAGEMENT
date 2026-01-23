import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./MemberDetails.css";

function MemberDetails() {
  const { id } = useParams();
  const [member, setMember] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/members/${id}`)
      .then((res) => res.json())
      .then((data) => setMember(data))
      .catch((err) => console.log(err));
  }, [id]);

  if (!member) return <h2>Loading...</h2>;

  return (
    <div className="member-details-container">
      {/* LEFT - PHOTO */}
      <div className="member-photo">
        <img
          src={member.photoUrl || "https://via.placeholder.com/200"}
          alt="Member"
        />
      </div>

      {/* RIGHT - DETAILS */}
      <div className="member-info">
        <h2>{member.name}</h2>
        <p>
          <strong>Email:</strong> {member.email}
        </p>
        <p>
          <strong>Phone:</strong> {member.phone}
        </p>
        <p>
          <strong>Joining Date:</strong> {member.joiningDate}
        </p>
        <p>
          <strong>Fee:</strong> ₹{member.fee}
        </p>
        <p>
          <strong>Duration:</strong> {member.duration}
        </p>
      </div>
    </div>
  );
}

export default MemberDetails;
