import { useState, useEffect } from 'react';


const useNumberFormatter = (number) => {
    const [formattedNumber, setFormattedNumber] = useState(null);

    useEffect(() => {
        if (number !== null && number !== undefined && !isNaN(number)) {
            setFormattedNumber(
                new Intl.NumberFormat('en-US').format(number)
            );
        }
    }, [number]);
    return formattedNumber;
};



export default useNumberFormatter;
