import { createRoot } from 'react-dom/client'
import App from "./App.jsx";
import '@/sass/style.scss';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { StrictMode } from "react";
import { HelmetProvider } from "react-helmet-async";

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            cacheTime: Infinity
        }
    }
});

createRoot(document.getElementById('app_container')).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
                <HelmetProvider>
                    <App />
                </HelmetProvider>
            <ReactQueryDevtools initialIsOpen={false} />
        </QueryClientProvider>
    </StrictMode>
)
