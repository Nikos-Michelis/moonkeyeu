import Footer from "@/layout/Footer.jsx";
import {Outlet, useLocation} from "react-router-dom";
import {GetFallbackComponent} from "@/components/fallback/GetFallbackComponent.jsx";
import {ErrorBoundary} from "react-error-boundary";

function PageLayout() {
    const location = useLocation();
    const showFooter = location.pathname !== '/locations';

    return (
        <>
            <main className="container" data-type="full-bleed" data-spacing="none">
                <ErrorBoundary FallbackComponent={GetFallbackComponent} resetKeys={[location.key]} >
                    <Outlet />
                </ErrorBoundary>
            </main>
            {showFooter && <Footer />}
        </>
    );
}

export default PageLayout;