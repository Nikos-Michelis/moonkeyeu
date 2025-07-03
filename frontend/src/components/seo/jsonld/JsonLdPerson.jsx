import {Helmet} from "react-helmet-async";

const JsonLdPerson = (
    {
        name,
        birthDate,
        job = "Astronaut",
        nationality,
        agency
    }) => {
    const fullTitle = `MoonkeyEU - ${name || "Space Launch Tracker"}`;

    const jsonLd = {
        "@context": "http://schema.org/",
        "@type": "Person",
        "name": {fullTitle},
        ...(birthDate && {"birthDate": birthDate}),
        ...(nationality && {"nationality": nationality}),
        "jobTitle": {
            "@type": "DefinedTerm",
            "name": {job}
        },
        "worksFor": {
            "@type": "Organization",
            "name": {agency}
        }
    }
    return (
        <Helmet>
            <script type="application/ld+json">
                {JSON.stringify(jsonLd)}
            </script>
        </Helmet>
    );
}

export default JsonLdPerson
