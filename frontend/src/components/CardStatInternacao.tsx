export function CardStatInternacao({text, number,}: {text: string;number: number;}) {
  return (
    <div className="bg-white rounded-2xl border-t-5 border-principal flex flex-col gap-2 py-8 px-10 w-full">
      <p className="text-lg font-medium">{text}</p>
      <span className="text-4xl font-bold text-principal">{number}</span>
    </div>
  );
}