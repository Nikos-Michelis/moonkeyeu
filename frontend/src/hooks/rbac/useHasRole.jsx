import {useAuth} from "@/context/AuthProvider.jsx";

export const useHasRole = () => {
    const { user } = useAuth();

    const hasRole = (allowedRoles) => {
        return user?.role?.some(role => allowedRoles.includes(role));
    };

    return { hasRole };
};
