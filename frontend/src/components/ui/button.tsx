interface ButtonProps{
    submit?:boolean;
    texto: string;
    onClick?: () => void;
    ariaLabel?: string;
}

function PrimaryButton({submit, texto, onClick}:ButtonProps){
    return(
        <button 
        type={submit ? "submit" : "button"}
        onClick={onClick}
        className="rounded-full py-2 px-4 text-white font-semibold bg-principal"
        >    
            {texto}
        </button>
    );
}

function SecondaryButton({submit, texto, onClick}:ButtonProps){
    return(
        <button 
        type={submit ? "submit" : "button"}
        onClick={onClick}
        className="rounded-full py-2 px-4 text-black font-semibold border border-destaque"
        >    
            {texto}
        </button>
    );
}

function DangerButton({submit, texto, onClick, ariaLabel}:ButtonProps){
    return(
        <button 
        type={submit ? "submit" : "button"}
        onClick={onClick}
        aria-label={ariaLabel}
        className="rounded-full py-2 px-4 text-white font-semibold bg-red-600 hover:bg-red-700"
        >    
            {texto}
        </button>
    );
}

export {
  PrimaryButton,
  SecondaryButton,
  DangerButton
}