interface ButtonProps{
    submit?:boolean;
    texto: string;
}

function PrimaryButton({submit, texto}:ButtonProps){
    return(
        <button 
        type={submit ? "submit" : "button"}
        className="rounded-full py-2 px-4 text-white font-semibold bg-principal"
        >    
            {texto}
        </button>
    );
}

function SecondaryButton({submit, texto}:ButtonProps){
    return(
        <button 
        type={submit ? "submit" : "button"}
        className="rounded-full py-2 px-4 text-black font-semibold border border-destaque"
        >    
            {texto}
        </button>
    );
}

export {
  PrimaryButton,
  SecondaryButton
}