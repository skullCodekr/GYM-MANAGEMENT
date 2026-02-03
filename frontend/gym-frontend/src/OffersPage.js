import React from "react";

const offersData = [
  {
    id: 1,
    title: "New Year Special - 20% OFF",
    description: "Get 20% discount on all annual memberships.",
    validTill: "2026-02-28",
  },
  {
    id: 2,
    title: "Summer Offer - Free Personal Training",
    description: "Sign up for 6 months and get 1 month personal training free.",
    validTill: "2026-05-31",
  },
  {
    id: 3,
    title: "Referral Bonus",
    description: "Refer a friend and both get 10% off next renewal.",
    validTill: "2026-12-31",
  },
];

function isOfferExpired(dateStr) {
  const today = new Date();
  const validDate = new Date(dateStr);
  return validDate < today;
}

const OffersPage = () => {
  return (
    <div style={{ padding: "20px", maxWidth: "900px", margin: "auto" }}>
      <h1 style={{ textAlign: "center", marginBottom: "20px" }}>
        Current Offers
      </h1>

      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit,minmax(280px,1fr))",
          gap: "20px",
        }}
      >
        {offersData.map(({ id, title, description, validTill }) => {
          const expired = isOfferExpired(validTill);

          return (
            <div
              key={id}
              style={{
                border: "1px solid #ddd",
                borderRadius: "8px",
                padding: "15px",
                boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
                backgroundColor: expired ? "#f8d7da" : "#e6ffed",
                color: expired ? "#842029" : "#0f5132",
                position: "relative",
              }}
            >
              {expired && (
                <span
                  style={{
                    position: "absolute",
                    top: "10px",
                    right: "10px",
                    backgroundColor: "#842029",
                    color: "white",
                    padding: "2px 8px",
                    borderRadius: "4px",
                    fontSize: "12px",
                    fontWeight: "bold",
                  }}
                >
                  EXPIRED
                </span>
              )}

              <h2 style={{ marginTop: 0 }}>{title}</h2>
              <p>{description}</p>
              <p
                style={{
                  fontStyle: "italic",
                  fontSize: "14px",
                  marginTop: "10px",
                }}
              >
                Valid Till: {new Date(validTill).toLocaleDateString()}
              </p>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default OffersPage;
