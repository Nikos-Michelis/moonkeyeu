import React from "react";
import ContactForm from "@/components/modal/forms/ContactForm.jsx";
import Head from "@/components/seo/Head.jsx";
import JsonLdGeneric from "@/components/seo/jsonld/JsonLdGeneric.jsx";
import ScrollToTop from "@/components/utils/ScrollToTop.jsx";

function Contact(){
    return(
        <>
            <Head
                title="Contact"
                description="Get in touch with us for inquiries, feedback."
            />
            <JsonLdGeneric
                title="Contact"
                description="Get in touch with us for inquiries, feedback."
            />
            <ScrollToTop behavior="auto" />
            <ContactForm />
        </>
    );
}
export default Contact;