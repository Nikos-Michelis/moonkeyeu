import {useAuth} from "@/context/AuthProvider.jsx";
import {Navigate} from "react-router-dom";

export function ProtectedRoutes({ children }) {
    const { user } = useAuth();

    if (!user) {
        return <Navigate to="/launches" />;
    }

    return <>{children}</>;
}
